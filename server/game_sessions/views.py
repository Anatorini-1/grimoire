from django.shortcuts import render

from game_sessions.models import (
    CampaignSession,
    CampaignSessionConnectedPlayers,
    SessionMessage,
)
from rest_framework import viewsets, status
from rest_framework.response import Response
from rest_framework.request import Request
from rest_framework.decorators import action
from rest_framework.exceptions import ValidationError

from channels.layers import get_channel_layer
from asgiref.sync import async_to_sync

from game_sessions.serializers import (
    SessionConnectedPlayerSerializer,
    SessionMessageSerializer,
    SessionSerializer,
)
from campaigns.models import CampaignPlayer
# Create your views here.


class CampaignSessionViewSet(viewsets.ModelViewSet):
    queryset = CampaignSession.objects.all()
    serializer_class = SessionSerializer

    @action(detail=True, methods=["post"], url_path="end")
    def end(self, request: Request, pk=None):
        session: CampaignSession = self.get_object()
        session.active = False
        session.token = None
        session.save()
        return Response(status=status.HTTP_200_OK)
        pass

    @action(detail=True, methods=["get"], url_path="messages")
    def messages(self, request: Request, pk=None):
        session: CampaignSession = self.get_object()
        messages = session.messages.all()
        serializer = SessionMessageSerializer(
            many=True, instance=messages, context={"request": request}
        )
        return Response(data=serializer.data, status=status.HTTP_200_OK)

        pass

    @action(detail=True, methods=["post"], url_path="send-message")
    def send_message(self, request: Request, pk=None):
        session: CampaignSession = self.get_object()
        if not session.active:
            return Response(
                status=status.HTTP_400_BAD_REQUEST,
                data={"message": "Session is inactive"},
            )
        if not self.request.user or not self.request.user.is_authenticated:
            return Response(status=status.HTTP_401_UNAUTHORIZED)
        if CampaignPlayer.objects.filter(
            player=self.request.user, campaign=session.campaign
        ).exists():
            player: CampaignPlayer = CampaignPlayer.objects.get(
                player=self.request.user, campaign=session.campaign
            )
        elif session.campaign.dm == self.request.user:
            player = CampaignPlayer.objects.create(
                campaign=session.campaign,
                player=self.request.user,
                character=None,
                accepted=False,
            )
        else:
            return Response(
                data={"You are not a member of this campaign"},
                status=status.HTTP_401_UNAUTHORIZED,
            )

        if "message" not in request.data:
            return Response(
                data={"message": "This field is required"},
                status=status.HTTP_400_BAD_REQUEST,
            )

        message = SessionMessage.objects.create(
            session=session,
            sender=player,
            message=request.data["message"],
        )
        output = SessionMessageSerializer(
            instance=message, context={"request": request}
        )
        channel_layer = get_channel_layer()
        async_to_sync(channel_layer.group_send)(
            session.token,
            {
                "type": "chat_message",
                "payload": output.data,
            },
        )
        return Response(
            status=status.HTTP_201_CREATED,
            data=output.data,
        )

    @action(detail=True, methods=["get"], url_path="connected-players")
    def connected_players(self, request, pk=None):
        instances = CampaignSessionConnectedPlayers.objects.filter(
            session=self.get_object()
        )

        res = SessionConnectedPlayerSerializer(
            instance=instances, many=True, context={"request": request}
        )
        return Response(data=res.data)


class SessionMessageViewSet(viewsets.ModelViewSet):
    queryset = SessionMessage.objects.all()
    serializer_class = SessionMessageSerializer
