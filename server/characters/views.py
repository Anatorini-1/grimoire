from ctypes import alignment
from django.shortcuts import render
from rest_framework import viewsets

from library.models import Alignment, Race, Background, Class
from .models import Character, NewCharacter
from .serializers import (
    CharacterSerializer,
    NewCharacterSerializer,
    UnauthorizedCharacterSerializer,
)
from django.shortcuts import render
from django.shortcuts import get_object_or_404

# Create your views here.
from django.http import JsonResponse, HttpRequest
from django.views import View
from django.contrib.auth.models import User, Group

from rest_framework import permissions, viewsets
from rest_framework.response import Response
from rest_framework.pagination import PageNumberPagination
from main.permissions import IsCharacterOwnerOrDM
from rest_framework import status
from django.contrib.auth.models import User
# Create your views here.


class CharacterViewSet(viewsets.ModelViewSet):
    queryset = NewCharacter.objects.all()
    serializer_class = UnauthorizedCharacterSerializer
    permission_classes = [permissions.IsAuthenticated, IsCharacterOwnerOrDM]

    def list(self, request):
        queryset = NewCharacter.objects.filter(player=request.user)
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

    def retrieve(self, request, pk=None):
        queryset = NewCharacter.objects.all()
        character = get_object_or_404(queryset, pk=pk)
        if not request.user == character.player:
            return Response(
                status=status.HTTP_401_UNAUTHORIZED,
                data={"error": "Only the characters owner can view its details"},
            )

        serializer = NewCharacterSerializer(
            instance=character, context={"request": request}
        )
        return Response(serializer.data)

    def create(self, request, *args, **kwargs):
        def url_to_model(model, url):
            return model.objects.get(pk=url.split("/")[-2])

        # Access data from the request
        data = request.data

        # Add custom logic (e.g., modifying data or validating conditions)
        required = ["name"]
        errors = {}
        for r in required:
            if r not in data:
                errors[r] = f"Field {r} is required"
        if len(errors) > 0:
            return Response(data=errors, status=status.HTTP_400_BAD_REQUEST)

        instance = NewCharacter.objects.create(
            name=data["name"],
            player=request.user,
        )
        out = self.get_serializer(instance=instance)
        return Response(out.data, status=status.HTTP_201_CREATED)
