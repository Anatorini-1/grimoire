from django.shortcuts import render
from rest_framework import viewsets
from .models import Character
from .serializers import CharacterSerializer
from django.shortcuts import render

# Create your views here.
from django.http import JsonResponse, HttpRequest
from django.views import View
from django.contrib.auth.models import User, Group

from rest_framework import permissions, viewsets
from rest_framework.response import Response
from rest_framework.pagination import PageNumberPagination
from main.permissions import IsCharacterOwnerOrDM
# Create your views here.


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
