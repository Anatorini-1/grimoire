import logging
from multiprocessing import context
import stat
from django.shortcuts import get_object_or_404, render
from rest_framework import viewsets, status
from rest_framework.response import Response
from rest_framework.decorators import action
import secrets
import campaigns
from characters.models import Character, NewCharacter
from game_sessions.models import CampaignSession
from game_sessions.serializers import SessionSerializer
from .models import Campaign, CampaignPlayer, CampaignChatMessage
from .serializers import (
    CampaignChatMessageSerializer,
    CampaignPlayerSerializer,
    CampaignSerializer,
)
from django.contrib.auth.models import User
from rest_framework.permissions import IsAuthenticated
from rest_framework.authentication import TokenAuthentication
import ipdb
from django.db.models import Q


class CampaignViewSet(viewsets.ModelViewSet):
    queryset = Campaign.objects.all()
    serializer_class = CampaignSerializer

    @action(detail=True, methods=["post"], url_path="invite-player")
    def invite_player(self, request, pk=None):
        campaign: Campaign = self.get_object()
        user = request.user
        if "player" not in request.data:
            return Response(
                data={"player": "This field is required"},
                status=status.HTTP_400_BAD_REQUEST,
            )
        player = request.data["player"]

        if not campaign.dm == user:
            return Response(
                data={"Only the campaign's DM can invite players"},
                status=status.HTTP_401_UNAUTHORIZED,
            )
        if not player or not User.objects.filter(pk=player.split("/")[-2]).exists():
            return Response(
                data={"player": f"The requested user does not exist: {player}"},
                status=status.HTTP_400_BAD_REQUEST,
            )
        player = User.objects.get(pk=player.split("/")[-2])
        instance = CampaignPlayer.objects.create(
            campaign=campaign, player=player, character=None, accepted=False
        )
        output = CampaignPlayerSerializer(
            instance=instance, context={"request": request}
        )
        return Response(status=status.HTTP_201_CREATED, data=output.data)

    @action(detail=True, methods=["post"], url_path="create-session")
    def create_session(self, request, pk=None):
        campaign: Campaign = self.get_object()
        session = CampaignSession.objects.create(
            campaign=campaign, active=True, token=secrets.token_urlsafe(40)
        )
        output = SessionSerializer(instance=session, context={"request": request})
        return Response(
            status=status.HTTP_201_CREATED,
            data=output.data,
        )

    @action(detail=True, methods=["post"], url_path="accept-invite")
    def accept_invite(self, request, pk=None):
        campaign: Campaign = self.get_object()
        user = request.user
        if "character" not in request.data:
            return Response(
                data={"character": "This field is required"},
                status=status.HTTP_400_BAD_REQUEST,
            )
        character = request.data["character"].split("/")[-2]
        character = get_object_or_404(NewCharacter, pk=int(character))
        invite = get_object_or_404(CampaignPlayer, campaign=campaign, player=user)
        invite.accepted = True
        invite.character = character
        invite.save()
        res = CampaignPlayerSerializer(instance=invite, context={"request": request})
        return Response(data=res.data, status=status.HTTP_202_ACCEPTED)

    def list(self, request):
        user = request.user
        print(user)
        queryset = self.filter_queryset(
            self.get_queryset()
            .filter(Q(dm=user) | Q(players__player=user))
            .distinct()  # Ensures unique results
        )
        page = self.paginate_queryset(queryset)  # Paginate the queryset
        if page is not None:
            serializer = self.get_serializer(page, many=True)
            print(len(serializer.data))
            return self.get_paginated_response(serializer.data)

        serializer = self.get_serializer(queryset, many=True)
        return Response(serializer.data)

    def create(self, request, *args, **kwargs):
        serializer = self.get_serializer(data=request.data)
        serializer.is_valid(raise_exception=True)

        # Save the new instance
        instance = serializer.save(dm=self.request.user)

        # Serialize the output data
        output_serializer = self.get_serializer(instance)
        output_data = output_serializer.data

        # Get the related session and send a message
        CampaignPlayer.objects.create(
            campaign=instance, player=request.user, character=None, accepted=False
        )

        return Response(output_data, status=status.HTTP_201_CREATED)


class CampaignPlayers(viewsets.ModelViewSet):
    queryset = CampaignPlayer.objects.all()
    serializer_class = CampaignPlayerSerializer


class CampaignChatMessages(viewsets.ModelViewSet):
    queryset = CampaignChatMessage.objects.all()
    serializer_class = CampaignChatMessageSerializer
