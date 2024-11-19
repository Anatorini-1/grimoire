from django.contrib.auth.models import Group, User
from rest_framework import serializers

from main.models import (
    Spell,
    Character,
    Item,
    Class,
    Campaign,
    CharacterItem,
    Alignment,
    Race,
    Background,
)


class UserSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = User
        fields = ["url", "username", "email"]


class GroupSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Group
        fields = ["url", "name"]


class SpellSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Spell
        fields = [
            "url",
            "name",
            "description",
            "level",
            "ritual",
            "school",
            "range",
            "duration",
        ]


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


# class StatisticsSerializer(serializers.HyperlinkedModelSerializer):
#     def validate(self,data):
#         required_fields = ["strength", "dexterity", "constitution", "intelligence", "wisdom", "charisma"]
#         errors = {}
#         for field in required_fields:
#             if field not in data:
#                 errors[field] = (f"Field {field} missing in request body")
#             if int(data[field]) <= 0:
#                 errors[field] = (f"Field {field} must be grater than zero")
#         if errors:
#             raise serializers.ValidationError(errors)
#         return data

#     class Meta:
#         model = Statistics
#         fields = ["url", "strength", "dexterity", "constitution", "intelligence", "wisdom", "charisma" ]


class ItemSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Item
        fields = [
            "url",
            "name",
            "weight",
            "value",
            "weapon",
            "attackBonus",
            "damage",
            "description",
        ]


class CharacterItemSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = CharacterItem
        fields = ["url", "item", "character", "quantity"]


class CampaignSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Campaign
        fields = ["url", "dm", "name"]


class ClassSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Class
        fields = ["url", "name", "spellcastingAbility"]


class AligmentSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Alignment
        fields = ["url", "name"]


class RaceSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Race
        fields = ["url", "name"]


class BackgroundSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Background
        fields = ["url", "name"]
