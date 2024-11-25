from django.shortcuts import render
from .models import (
    Background,
    Alignment,
    Race,
    Item,
    Spell,
    Skill,
    Class,
    Statistic,
    Feat,
)
from rest_framework import viewsets, status
from rest_framework.response import Response
from .serializers import (
    FeatSerializer,
    SkillSerializer,
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

    def get_queryset(self):
        if self.request.user.is_authenticated:
            return self.queryset.filter(created_by__isnull=True) | self.queryset.filter(
                created_by=self.request.user
            )
        return self.queryset.filter(created_by__isnull=True)

    def create(self, request, *args, **kwargs):
        if not request.user.is_authenticated:
            return Response(
                {"detail": "Authentication required for creating items."},
                status=status.HTTP_401_UNAUTHORIZED,
            )

        # Set the created_by field to the authenticated user
        request.data["created_by"] = request.user.id
        return super().create(request, *args, **kwargs)


class ClassViewSet(viewsets.ModelViewSet):
    queryset = Class.objects.all()
    serializer_class = ClassSerializer


class ItemViewSet(viewsets.ModelViewSet):
    queryset = Item.objects.all()
    serializer_class = ItemSerializer

    def get_queryset(self):
        if self.request.user.is_authenticated:
            return self.queryset.filter(created_by__isnull=True) | self.queryset.filter(
                created_by=self.request.user
            )
        return self.queryset.filter(created_by__isnull=True)

    def create(self, request, *args, **kwargs):
        if not request.user.is_authenticated:
            return Response(
                {"detail": "Authentication required for creating items."},
                status=status.HTTP_401_UNAUTHORIZED,
            )

        # Set the created_by field to the authenticated user
        request.data["created_by"] = request.user.id
        return super().create(request, *args, **kwargs)


class RaceViewSet(viewsets.ModelViewSet):
    queryset = Race.objects.all()
    serializer_class = RaceSerializer

    def get_queryset(self):
        if self.request.user.is_authenticated:
            return self.queryset.filter(created_by__isnull=True) | self.queryset.filter(
                created_by=self.request.user
            )
        return self.queryset.filter(created_by__isnull=True)

    def create(self, request, *args, **kwargs):
        if not request.user.is_authenticated:
            return Response(
                {"detail": "Authentication required for creating items."},
                status=status.HTTP_401_UNAUTHORIZED,
            )

        # Set the created_by field to the authenticated user
        request.data["created_by"] = request.user.id
        return super().create(request, *args, **kwargs)


class AlignmentViewSet(viewsets.ModelViewSet):
    queryset = Alignment.objects.all()
    serializer_class = AligmentSerializer

    def get_queryset(self):
        if self.request.user.is_authenticated:
            return self.queryset.filter(created_by__isnull=True) | self.queryset.filter(
                created_by=self.request.user
            )
        return self.queryset.filter(created_by__isnull=True)

    def create(self, request, *args, **kwargs):
        if not request.user.is_authenticated:
            return Response(
                {"detail": "Authentication required for creating items."},
                status=status.HTTP_401_UNAUTHORIZED,
            )

        # Set the created_by field to the authenticated user
        request.data["created_by"] = request.user.id
        return super().create(request, *args, **kwargs)


class BackgroundViewSet(viewsets.ModelViewSet):
    queryset = Background.objects.all()
    serializer_class = BackgroundSerializer

    def get_queryset(self):
        if self.request.user.is_authenticated:
            return self.queryset.filter(created_by__isnull=True) | self.queryset.filter(
                created_by=self.request.user
            )
        return self.queryset.filter(created_by__isnull=True)

    def create(self, request, *args, **kwargs):
        print(request)
        if not request.user.is_authenticated:
            return Response(
                {"detail": "Authentication required for creating items."},
                status=status.HTTP_401_UNAUTHORIZED,
            )

        # Set the created_by field to the authenticated user
        request.data["created_by"] = request.user.id
        return super().create(request, *args, **kwargs)


class StatisticViewSet(viewsets.ModelViewSet):
    queryset = Statistic.objects.all()
    serializer_class = StatisticSerializer

    def get_queryset(self):
        if self.request.user.is_authenticated:
            return self.queryset.filter(created_by__isnull=True) | self.queryset.filter(
                created_by=self.request.user
            )
        return self.queryset.filter(created_by__isnull=True)

    def create(self, request, *args, **kwargs):
        if not request.user.is_authenticated:
            return Response(
                {"detail": "Authentication required for creating items."},
                status=status.HTTP_401_UNAUTHORIZED,
            )

        # Set the created_by field to the authenticated user
        request.data["created_by"] = request.user.id
        return super().create(request, *args, **kwargs)


class SkillViewSet(viewsets.ModelViewSet):
    queryset = Skill.objects.all()
    serializer_class = SkillSerializer

    def get_queryset(self):
        if self.request.user.is_authenticated:
            return self.queryset.filter(created_by__isnull=True) | self.queryset.filter(
                created_by=self.request.user
            )
        return self.queryset.filter(created_by__isnull=True)

    def create(self, request, *args, **kwargs):
        if not request.user.is_authenticated:
            return Response(
                {"detail": "Authentication required for creating items."},
                status=status.HTTP_401_UNAUTHORIZED,
            )

        # Set the created_by field to the authenticated user
        request.data["created_by"] = request.user.id
        return super().create(request, *args, **kwargs)


class FeatViewSet(viewsets.ModelViewSet):
    queryset = Feat.objects.all()
    serializer_class = FeatSerializer

    def get_queryset(self):
        if self.request.user.is_authenticated:
            return self.queryset.filter(created_by__isnull=True) | self.queryset.filter(
                created_by=self.request.user
            )
        return self.queryset.filter(created_by__isnull=True)

    def create(self, request, *args, **kwargs):
        if not request.user.is_authenticated:
            return Response(
                {"detail": "Authentication required for creating items."},
                status=status.HTTP_401_UNAUTHORIZED,
            )

        # Set the created_by field to the authenticated user
        request.data["created_by"] = request.user.id
        return super().create(request, *args, **kwargs)
