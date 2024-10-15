from django.shortcuts import render

# Create your views here.
from django.http import JsonResponse, HttpRequest
from django.views import View
from django.contrib.auth.models import User, Group

from rest_framework import permissions, viewsets
from rest_framework.response import Response
from rest_framework.pagination import PageNumberPagination

from main.serializers import (
    UserSerializer,
    GroupSerializer,
    SpellSerializer,
    CharacterSerializer,
    CampaignSerializer,
    ClassSerializer,
    ItemSerializer,
    RaceSerializer,
    AligmentSerializer,
    BackgroundSerializer,
)
from main.models import *
from main.permissions import IsCharacterOwnerOrDM


class TestView(View):
    def get(self, request: HttpRequest) -> JsonResponse:
        return JsonResponse({"dupa": "dupa"})


class UserViewSet(viewsets.ReadOnlyModelViewSet):
    queryset = User.objects.all()
    serializer_class = UserSerializer
    permission_classes = [
        permissions.IsAuthenticated,
    ]


class GroupViewSet(viewsets.ModelViewSet):
    queryset = Group.objects.all()
    serializer_class = GroupSerializer
    permission_classes = [permissions.IsAuthenticated]


class SpellViewSet(viewsets.ModelViewSet):
    queryset = Spell.objects.all()
    serializer_class = SpellSerializer


class CharacterViewSet(viewsets.ModelViewSet):
    queryset = Character.objects.all()
    serializer_class = CharacterSerializer
    permission_classes = [permissions.IsAuthenticated, IsCharacterOwnerOrDM]

    def list(self, request):
        queryset = Character.objects.filter(player=request.user)
        paginator = PageNumberPagination()
        page = paginator.paginate_queryset(queryset, request)

        if page is not None:
            serializer = self.serializer_class(
                page, many=True, context={"request": request}
            )
            return paginator.get_paginated_response(serializer.data)

        serializer = self.serializer_class(
            queryset, many=True, context={"request": request}
        )
        return Response(serializer.data)


class CampaignViewSet(viewsets.ModelViewSet):
    queryset = Campaign.objects.all()
    serializer_class = CampaignSerializer


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
