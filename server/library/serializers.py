from .models import (
    Background,
    Alignment,
    Feat,
    Race,
    Item,
    Spell,
    Skill,
    Class,
    Statistic,
)
from rest_framework import serializers


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


class StatisticSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Statistic
        fields = ["url", "name"]


class SkillSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Skill
        fields = ["url", "name", "statistic"]


class FeatSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Feat
        fields = ["url", "name", "description"]
