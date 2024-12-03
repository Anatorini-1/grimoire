from django.db.models import (
    Model,
    PROTECT,
    CASCADE,
    ForeignKey,
    CharField,
    IntegerField,
    TextField,
    OneToOneField,
    BooleanField,
)
from django.contrib.auth.models import User
from library.models import Background, Alignment, Race, Item, Spell, Skill, Class


class CharacterInfo(Model):
    age = IntegerField()
    height = IntegerField()
    weight = IntegerField()
    eyes = CharField(max_length=16)
    skin = CharField(max_length=32)
    hair = CharField(max_length=64)
    allies_and_orgs = TextField()
    appearance = TextField()
    backstory = TextField()
    treasure = TextField()
    additionalFeaturesAndTraits = TextField()


class CasterInfo(Model):
    spellcastingClass = ForeignKey(Class, on_delete=PROTECT)


class SpellSlotsExpended(Model):
    spellCasterInfo = ForeignKey(CasterInfo, on_delete=CASCADE)
    level = IntegerField()
    count = IntegerField()


class Character(Model):
    name = CharField(max_length=128)
    player = ForeignKey(
        User,
        on_delete=PROTECT,
    )
    # classname = ForeignKey(Class, on_delete=PROTECT, related_name="classname")
    # caster_info = OneToOneField(CasterInfo, null=True, on_delete=PROTECT)
    # experience = IntegerField(default=0)
    # info = OneToOneField(CharacterInfo, on_delete=PROTECT, null=True)
    # background = ForeignKey(Background, on_delete=PROTECT)
    # alignment = ForeignKey(Alignment, on_delete=PROTECT)
    # race = ForeignKey(Race, on_delete=PROTECT)
    # deathSaveSuccess = IntegerField(default=0)
    # deathSaveFailure = IntegerField(default=0)
    # temporaryHitpoint = IntegerField(default=0)


class NewCharacter(Model):
    name = CharField(max_length=128)
    player = ForeignKey(User, on_delete=PROTECT, related_name="player")
    classname = ForeignKey(Class, on_delete=PROTECT, related_name="classname")
    caster_info = OneToOneField(CasterInfo, null=True, on_delete=PROTECT)
    experience = IntegerField(default=0)
    info = OneToOneField(CharacterInfo, on_delete=PROTECT)
    background = ForeignKey(Background, on_delete=PROTECT)
    alignment = ForeignKey(Alignment, on_delete=PROTECT)
    race = ForeignKey(Race, on_delete=PROTECT)
    deathSaveSuccess = IntegerField(default=0)
    deathSaveFailure = IntegerField(default=0)
    temporaryHitpoint = IntegerField(default=0)


class CharacterItem(Model):
    item = ForeignKey(Item, on_delete=PROTECT)
    character = ForeignKey(Character, on_delete=CASCADE, related_name="items")
    quantity = IntegerField()


class CharacterStatistic(Model):
    character = ForeignKey(Character, on_delete=CASCADE, related_name="statistics")
    value = IntegerField()


class CharacterSkill(Model):
    character = ForeignKey(Character, on_delete=CASCADE, related_name="skills")
    skill = ForeignKey(Skill, on_delete=PROTECT)
    proficiency = BooleanField(default=False)
    expertise = BooleanField(default=False)


class CharacterEquipment(Model):
    character = ForeignKey(Character, on_delete=CASCADE, related_name="equipment")
    cp = IntegerField()
    sp = IntegerField()
    ep = IntegerField()
    gp = IntegerField()
    pp = IntegerField()


class CharacterSpells(Model):
    character = ForeignKey(Character, on_delete=CASCADE, related_name="spells")
    spell = ForeignKey(Spell, on_delete=CASCADE)
    prepared = BooleanField(default=False)


# Create your models here.
