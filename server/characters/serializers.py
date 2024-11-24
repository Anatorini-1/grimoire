from rest_framework import serializers
from .models import Character, CharacterItem


class CharacterSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Character
        fields = [
            "url",
            "name",
            "player",
            "campaign",
            "classname",
            "casterInfo",
            "experience",
            "info",
            "background",
            "alignment",
            "race",
            "deathSaveSuccess",
            "deathSaveFailure",
            "temporaryHitpoint",
        ]


class CharacterItemSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = CharacterItem
        fields = ["url", "item", "character", "quantity"]
