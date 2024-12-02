from rest_framework import serializers
from .models import Character, CharacterItem, NewCharacter


class CharacterSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Character
        fields = (
            [
                "url",
                # "name",
                # "player",
                # "classname",
                # "experience",
                # "info",
                # "background",
                # "alignment",
                # "race",
                # "deathSaveSuccess",
                # "deathSaveFailure",
                # "temporaryHitpoint",
                # "items",
                # "statistics",
                # "skills",
                # "equipment",
                # "spells",
            ],
        )
        # read_only_fields = [
        #     "items",
        #     "statistics",
        #     "skills",
        #     "equipment",
        #     "spells",
        #     "player",
        # ]


class NewCharacterSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = NewCharacter
        fields = ["url", "player", "name"]


class UnauthorizedCharacterSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = NewCharacter
        fields = ["url", "player", "name"]


class CharacterItemSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = CharacterItem
        fields = ["url", "item", "character", "quantity"]
