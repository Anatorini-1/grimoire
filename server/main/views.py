# Create your views here.
from django.http import JsonResponse, HttpRequest
from django.views import View
from django.contrib.auth.models import User, Group

from rest_framework import permissions, viewsets


class TestView(View):
    def get(self, request: HttpRequest) -> JsonResponse:
        return JsonResponse({"dupa": "dupa"})
