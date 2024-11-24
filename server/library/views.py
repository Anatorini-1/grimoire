from django.shortcuts import render
from .models import Background, Alignment, Race, Item, Spell, Skill, Class, Statistic
from rest_framework import viewsets
from .serializers import (
    SpellSerializer,
    ClassSerializer,
    ItemSerializer,
    RaceSerializer,
    AligmentSerializer,
    BackgroundSerializer,
    StatisticSerializer,
)
# Create your views here.


class SpellViewSet(viewsets.ModelViewSet):
    queryset = Spell.objects.all()
    serializer_class = SpellSerializer


class ClassViewSet(viewsets.ModelViewSet):
    queryset = Class.objects.all()
    serializer_class = ClassSerializer


class ItemViewSet(viewsets.ModelViewSet):
    queryset = Item.objects.all()
    serializer_class = ItemSerializer


class RaceViewSet(viewsets.ModelViewSet):
    queryset = Race.objects.all()
    serializer_class = RaceSerializer


class AlignmentViewSet(viewsets.ModelViewSet):
    queryset = Alignment.objects.all()
    serializer_class = AligmentSerializer


class BackgroundViewSet(viewsets.ModelViewSet):
    queryset = Background.objects.all()
    serializer_class = BackgroundSerializer


class StatisticViewSet(viewsets.ModelViewSet):
    queryset = Statistic.objects.all()
    serializer_class = StatisticSerializer
