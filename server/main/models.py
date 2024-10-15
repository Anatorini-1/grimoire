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


class Campaign(Model):
    dm = ForeignKey(User, on_delete=PROTECT)
    name = CharField(max_length=1024)


class Statistic(Model):
    name = CharField(max_length=32)


class Skill(Model):
    name = CharField(max_length=64)
    statistic = ForeignKey(Statistic, on_delete=PROTECT)


class Class(Model):
    name = CharField(max_length=64, unique=True)
    spellcasting_ability = ForeignKey(Statistic, null=True, on_delete=PROTECT)


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


class Feat(Model):
    name = CharField(max_length=32)
    description = TextField()


class CharacterInfo(Model):
    age = IntegerField()
    heigth = IntegerField()
    weight = IntegerField()
    eyes = CharField(max_length=16)
    skin = CharField(max_length=32)
    hair = CharField(max_length=64)
    allies_and_orgs = TextField()
    apperance = TextField()
    backstory = TextField()
    treasure = TextField()
    additional_features_and_traits = TextField()


class CasterInfo(Model):
    spellcasting_class = ForeignKey(Class, on_delete=PROTECT)


class SpellSlotsExpended(Model):
    spell_caster_info = ForeignKey(CasterInfo, on_delete=CASCADE)
    level = IntegerField()
    count = IntegerField()


class Character(Model):
    name = CharField(max_length=128)
    player = ForeignKey(User, on_delete=PROTECT, related_name="player")
    campaign = ForeignKey(Campaign, on_delete=CASCADE, null=True)
    classname = ForeignKey(Class, on_delete=PROTECT, related_name="classname")
    caster_info = OneToOneField(CasterInfo, null=True, on_delete=PROTECT)
    experience = IntegerField(default=0)
    info = OneToOneField(CharacterInfo, on_delete=PROTECT, null=True)
    background = ForeignKey(Background, on_delete=PROTECT)
    alignment = ForeignKey(Alignment, on_delete=PROTECT)
    race = ForeignKey(Race, on_delete=PROTECT)
    death_save_success = IntegerField(default=0)
    death_save_failure = IntegerField(default=0)
    temporary_hitpoint = IntegerField(default=0)


class Item(Model):
    name = CharField(max_length=256, unique=True)
    weight = DecimalField(decimal_places=2, max_digits=10)
    value = DecimalField(decimal_places=2, max_digits=10)
    weapon = BooleanField(default=False)
    attack_bonus = IntegerField(null=True)
    damage = CharField(max_length=128, null=True)
    description = TextField(null=True)


class CharacterItem(Model):
    item = ForeignKey(Item, on_delete=PROTECT)
    character = ForeignKey(Character, on_delete=CASCADE)
    quantity = IntegerField()


class CharacterStatistic(Model):
    character = ForeignKey(Character, on_delete=CASCADE)
    value = IntegerField()


class CharacterSkill(Model):
    character = ForeignKey(Character, on_delete=CASCADE)
    skill = ForeignKey(Skill, on_delete=PROTECT)
    proficiency = BooleanField(default=False)
    expertise = BooleanField(default=False)


class CharacterEquipment(Model):
    character = ForeignKey(Character, on_delete=CASCADE)
    cp = IntegerField()
    sp = IntegerField()
    ep = IntegerField()
    gp = IntegerField()
    pp = IntegerField()


class CharacterSpells(Model):
    character = ForeignKey(Character, on_delete=CASCADE)
    spell = ForeignKey(Spell, on_delete=CASCADE)
    prepared = BooleanField(default=False)