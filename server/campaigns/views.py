from django.shortcuts import render
from rest_framework import viewsets, status
from rest_framework.response import Response
from rest_framework.decorators import action
import secrets
from game_sessions.models import CampaignSession
from game_sessions.serializers import SessionSerializer
from .models import Campaign, CampaignPlayer, CampaignChatMessage
from .serializers import (
    CampaignChatMessageSerializer,
    CampaignPlayerSerializer,
    CampaignSerializer,
)


class CampaignViewSet(viewsets.ModelViewSet):
    queryset = Campaign.objects.all()
    serializer_class = CampaignSerializer

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


class CampaignPlayers(viewsets.ModelViewSet):
    queryset = CampaignPlayer.objects.all()
    serializer_class = CampaignPlayerSerializer


class CampaignChatMessages(viewsets.ModelViewSet):
    queryset = CampaignChatMessage.objects.all()
    serializer_class = CampaignChatMessageSerializer
