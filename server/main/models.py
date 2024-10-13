from django.db import models
from django.db.models import *
from django.contrib.auth.models import User


class Campaign(Model):
    dm = ForeignKey(User, on_delete=PROTECT)
    name = CharField(max_length=1024)

class Class(Model):
    name = CharField(max_length=64)
    
class Statisctics(Model):
    strength : IntegerField
    dexterity: IntegerField
    constitution : IntegerField
    intelligence : IntegerField
    wisdom : IntegerField
    charisma : IntegerField

class Character(Model):
    name = CharField(max_length=128)
    player = ForeignKey(User, on_delete=PROTECT, related_name="player")
    campaign = ForeignKey(Campaign, on_delete=CASCADE, null=True)
    classname = ForeignKey(Class, on_delete=PROTECT,related_name="classname")
    statisctics = OneToOneField(Statisctics, on_delete=PROTECT, related_name="statistics")
    

class Item(Model):
    name = CharField(max_length=256)
    weight = DecimalField(decimal_places=2, max_digits=10)
    value = DecimalField(decimal_places=2, max_digits=10)
     
class CharacterItem(Model):
    item = ForeignKey(Item, on_delete=PROTECT)
    character = ForeignKey(Character, on_delete=CASCADE)
    quantity = IntegerField()
    

class Spell(Model):
    name = CharField(max_length=256)
    description = TextField()
    level = IntegerField()
    

    