from rest_framework import serializers, status
from rest_framework.response import Response
from rest_framework.exceptions import ValidationError

from game_sessions.models import (
    CampaignSession,
    CampaignSessionConnectedPlayers,
    SessionMessage,
)


class SessionSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = CampaignSession
        fields = [
            "url",
            "campaign",
            "active",
            "created_at",
            "ended_at",
            "messages",
            "token",
        ]


from campaigns.serializers import CampaignPlayerSerializer


class SessionMessageSerializer(serializers.HyperlinkedModelSerializer):
    sender = CampaignPlayerSerializer()

    class Meta:
        model = SessionMessage
        fields = ["url", "session", "sender", "created_at", "message"]
        read_only_fields = ["sender", "created_at"]


class SessionConnectedPlayerSerializer(serializers.HyperlinkedModelSerializer):
    player = CampaignPlayerSerializer()

    class Meta:
        model = CampaignSessionConnectedPlayers
        fields = ["player"]
