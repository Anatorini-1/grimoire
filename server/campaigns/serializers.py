from rest_framework import serializers

from .models import Campaign, CampaignChatMessage, CampaignPlayer
from rest_framework.exceptions import ValidationError
from rest_framework.response import Response
from rest_framework import status


class CampaignSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Campaign
        fields = ["url", "dm", "name", "players", "sessions"]


class CampaignPlayerSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = CampaignPlayer
        fields = ["url", "campaign", "player", "character", "accepted"]


class CampaignChatMessageSerializer(serializers.HyperlinkedModelSerializer):
    def create(self, validated_data):
        user = self.context["request"].user
        if not user or not user.is_authenticated:
            return Response(
                {"detail": "Authentication required for posting messages."},
                status=status.HTTP_401_UNAUTHORIZED,
            )

        campaign = validated_data.pop("campaign", None)
        if not campaign:
            raise ValidationError({"campaign": "This field is required."})

        message = validated_data.pop("message", None)
        if not message:
            raise ValidationError({"message": "This field is required."})

        return CampaignChatMessage.objects.create(
            campaign=campaign, sender=user, message=message
        )

    class Meta:
        model = CampaignChatMessage
        fields = ["url", "campaign", "sender", "message", "created_at"]
        read_only_fields = ["sender", "created_at"]
