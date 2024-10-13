from django.shortcuts import render

# Create your views here.
from django.http import JsonResponse, HttpRequest
from django.views import View
from django.contrib.auth.models import User, Group

from rest_framework import permissions, viewsets
from main.serializers import SpellSerializer, UserSerializer, GroupSerializer
from main.models import Spell


class TestView(View):
    def get(self, request : HttpRequest) -> JsonResponse: 
        return JsonResponse({"dupa" : "dupa"})
    

class UserViewSet(viewsets.ModelViewSet):
    queryset = User.objects.all()
    serializer_class = UserSerializer
    # permission_classes = [permissions.IsAuthenticated]

class GroupViewSet(viewsets.ModelViewSet):
    queryset = Group.objects.all()
    serializer_class = GroupSerializer
    # permission_classes = [permissions.IsAuthenticated]
    

class SpellViewSet(viewsets.ModelViewSet):
    queryset = Spell.objects.all()
    serializer_class = SpellSerializer
    

    