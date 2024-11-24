from django.db import models

from django.db.models import (
    Model,
    PROTECT,
    CASCADE,
    ForeignKey,
    CharField,
    IntegerField,
    TextField,
    OneToOneField,
    DecimalField,
    BooleanField,
)
from django.contrib.auth.models import User


class Statistic(Model):
    name = CharField(max_length=32)


class Skill(Model):
    name = CharField(max_length=64)
    statistic = ForeignKey(Statistic, on_delete=PROTECT)


class Class(Model):
    name = CharField(max_length=64, unique=True)
    spellcastingAbility = ForeignKey(Statistic, null=True, on_delete=PROTECT)


class Background(Model):
    name = CharField(max_length=64, unique=True)


class Alignment(Model):
    name = CharField(max_length=64, unique=True)


class Race(Model):
    name = CharField(max_length=64, unique=True)


class Spell(Model):
    name = CharField(max_length=256, unique=True)
    description = TextField(null=True)
    level = IntegerField()
    ritual = BooleanField(default=False)
    school = CharField(max_length=64, default="")
    range = CharField(max_length=256, default="Touch")
    duration = CharField(max_length=256, default="")


class Feat(Model):
    name = CharField(max_length=32)
    description = TextField()


class Item(Model):
    name = CharField(max_length=256, unique=True)
    weight = DecimalField(decimal_places=2, max_digits=10)
    value = DecimalField(decimal_places=2, max_digits=10)
    weapon = BooleanField(default=False)
    attackBonus = IntegerField(null=True)
    damage = CharField(max_length=128, null=True)
    description = TextField(null=True)
