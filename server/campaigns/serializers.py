from rest_framework import serializers
from .models import Campaign


class CampaignSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Campaign
        fields = ["url", "dm", "name"]
