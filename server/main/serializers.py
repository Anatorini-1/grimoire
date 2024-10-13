from django.contrib.auth.models import Group,User
from rest_framework import serializers

from main.models import *



class UserSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model =  User
        fields = ["url", "username", "email"]
        
class GroupSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Group
        fields = ['url', 'name']
        


class SpellSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Spell
        fields = ["name", "description", "level"]