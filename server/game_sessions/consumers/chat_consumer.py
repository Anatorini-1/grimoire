import json
from channels.generic.websocket import WebsocketConsumer
from django.shortcuts import get_object_or_404
from rest_framework.response import Response
from asgiref.sync import async_to_sync
from django.contrib.auth.models import AnonymousUser

import campaigns
from campaigns.models import CampaignPlayer
from campaigns.views import CampaignPlayers
from game_sessions.models import CampaignSession, CampaignSessionConnectedPlayers

import ipdb


class ChatConsumer(WebsocketConsumer):
    def connect(self):
        self.room_name = self.scope["url_route"]["kwargs"]["session_name"]
        self.room_group_name = self.scope["url_route"]["kwargs"]["session_name"]
        if isinstance(self.scope["user"], AnonymousUser):
            self.close()
        async_to_sync(self.channel_layer.group_add)(
            self.room_group_name, self.channel_name
        )

        session = CampaignSession.objects.get(
            token=self.scope["url_route"]["kwargs"]["session_name"]
        )
        player = get_object_or_404(
            CampaignPlayer, campaign=session.campaign, player=self.scope["user"]
        )
        CampaignSessionConnectedPlayers.objects.create(session=session, player=player)

        async_to_sync(self.channel_layer.group_send)(
            self.room_group_name,
            {"type": "player_connected", "payload": f"{player.id}"},
        )
        self.accept()

    def disconnect(self, code):
        session = CampaignSession.objects.get(
            token=self.scope["url_route"]["kwargs"]["session_name"]
        )

        player = get_object_or_404(
            CampaignPlayer, campaign=session.campaign, player=self.scope["user"]
        )
        async_to_sync(self.channel_layer.group_send)(
            self.room_group_name,
            {
                "type": "player_disconnected",
                "payload": f"{player.id}",
            },
        )
        async_to_sync(self.channel_layer.group_discard)(
            self.room_group_name, self.channel_name
        )
        [
            x.delete()
            for x in CampaignSessionConnectedPlayers.objects.filter(
                session=session, player=player
            ).all()
        ]
        pass

    def receive(self, text_data=None, bytes_data=None):
        return
        if text_data is not None:
            text_data_json = json.loads(text_data)
        else:
            text_data_json = {"message": "No message bruh"}
        message = text_data_json["message"]
        async_to_sync(self.channel_layer.group_send)(
            self.room_group_name, {"type": "chat.message", "message": message}
        )

    def chat_message(self, event):
        payload = event["payload"]
        # Send message to WebSocket
        self.send(
            text_data=json.dumps(
                {"type": "chat_message", "payload": json.dumps(payload)}
            )
        )

    def player_disconnected(self, event):
        payload = event["payload"]
        # Send message to WebSocket
        self.send(
            text_data=json.dumps(
                {"type": "player_disconnected", "payload": json.dumps(payload)}
            )
        )

    def player_connected(self, event):
        payload = event["payload"]
        # Send message to WebSocket
        self.send(
            text_data=json.dumps(
                {"type": "player_connected", "payload": json.dumps(payload)}
            )
        )
