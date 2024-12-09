from pickletools import read_long1
from urllib.request import Request
from rest_framework import serializers

from game_sessions.models import CampaignSessionConnectedPlayers
from users.serializers import UserSerializer

from .models import Campaign, CampaignPlayer
from rest_framework.exceptions import ValidationError
from rest_framework.response import Response
from rest_framework import status

from game_sessions.serializers import SessionSerializer


class CampaignPlayerSerializer(serializers.HyperlinkedModelSerializer):
    player = UserSerializer()

    class Meta:
        model = CampaignPlayer
        fields = ["url", "campaign", "player", "character", "accepted"]


class CampaignSerializer(serializers.HyperlinkedModelSerializer):
    dm = UserSerializer(read_only=True)
    players = CampaignPlayerSerializer(many=True, read_only=True)
    sessions = SessionSerializer(many=True, read_only=True)
    accepted = serializers.SerializerMethodField()

    def get_accepted(self, instance):
        request = self.context["request"]
        user = request.user
        return CampaignPlayer.objects.filter(
            campaign=instance, player=user, accepted=True
        ).exists()

    class Meta:
        model = Campaign
        fields = ["url", "dm", "name", "players", "sessions", "accepted"]
        read_only_fields = ["players", "sessions", "dm"]
