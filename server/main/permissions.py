from rest_framework import permissions
from main.models import *


class IsCharacterOwnerOrDM(permissions.BasePermission):
    def has_object_permission(self, request, view, obj: Character):
        if request.user.is_superuser or request.user.is_staff:
            return True
        if obj.campaign is not None:
            if obj.campaign.dm == request.user: 
                return True
        return  request.user == obj.player