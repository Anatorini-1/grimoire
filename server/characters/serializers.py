from rest_framework import serializers
from .models import Character, CharacterItem


class CharacterSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Character
        fields = [
            "url",
            "name",
            "player",
            "classname",
            "experience",
            "info",
            "background",
            "alignment",
            "race",
            "deathSaveSuccess",
            "deathSaveFailure",
            "temporaryHitpoint",
            "items",
            "statistics",
            "skills",
            "equipment",
            "spells",
        ]


class CharacterItemSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = CharacterItem
        fields = ["url", "item", "character", "quantity"]
