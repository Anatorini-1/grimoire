from rest_framework import serializers

from library.serializers import (
    BackgroundSerializer,
    ClassSerializer,
    AligmentSerializer,
    RaceSerializer,
)
from .models import Character, CharacterInfo, CharacterItem, NewCharacter
import ipdb


class CharacterInfoSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = CharacterInfo
        fields = [
            "age",
            "height",
            "weight",
            "eyes",
            "skin",
            "hair",
            "allies_and_orgs",
            "appearance",
            "backstory",
            "treasure",
            "additionalFeaturesAndTraits",
        ]


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


class CharacterCreationSerializer(serializers.HyperlinkedModelSerializer):
    info = CharacterInfoSerializer()

    def create(self, validated_data):
        # Extract the 'info' data from the validated_data
        info_data = validated_data.pop("info")

        # Create the CharacterInfo instance
        character_info = CharacterInfo.objects.create(**info_data)

        # Create the NewCharacter instance and associate the created CharacterInfo
        new_character = NewCharacter.objects.create(
            info=character_info, **validated_data
        )

        return new_character

    class Meta:
        model = NewCharacter
        fields = [
            "url",
            "player",
            "name",
            "classname",
            "caster_info",
            "experience",
            "info",
            "background",
            "alignment",
            "race",
            "deathSaveSuccess",
            "deathSaveFailure",
            "temporaryHitpoint",
        ]
        read_only_fields = ["player", "info"]


class NewCharacterSerializer(serializers.HyperlinkedModelSerializer):
    classname = ClassSerializer()
    background = BackgroundSerializer()
    alignment = AligmentSerializer()
    race = RaceSerializer()
    info = CharacterInfoSerializer()

    class Meta:
        model = NewCharacter
        fields = [
            "url",
            "player",
            "name",
            "classname",
            "caster_info",
            "experience",
            "info",
            "background",
            "alignment",
            "race",
            "deathSaveSuccess",
            "deathSaveFailure",
            "temporaryHitpoint",
        ]
        read_only_fields = ["player"]


class UnauthorizedCharacterSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = NewCharacter
        fields = ["url", "player", "name"]


class CharacterItemSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = CharacterItem
        fields = ["url", "item", "character", "quantity"]
