import random
from django.contrib.auth.models import User
from library.models import (
    Statistic,
    Skill,
    Class,
    Background,
    Alignment,
    Race,
    Spell,
    Feat,
    Item,
)

# Create a sample admin user
admin_user = User.objects.create_user(
    username="admin", email="admin@example.com", password="password"
)

# Statistics
stats = ["Strength", "Dexterity", "Constitution", "Intelligence", "Wisdom", "Charisma"]
stat_objs = [
    Statistic.objects.create(name=stat, created_by=admin_user) for stat in stats
]

# Skills
skills = ["Athletics", "Acrobatics", "Stealth", "Arcana", "Perception", "Persuasion"]
skill_objs = [
    Skill.objects.create(
        name=skill, statistic=random.choice(stat_objs), created_by=admin_user
    )
    for skill in skills
]

# Classes
classes = ["Fighter", "Wizard", "Cleric", "Rogue"]
class_objs = [
    Class.objects.create(
        name=cls, spellcastingAbility=random.choice(stat_objs), created_by=admin_user
    )
    for cls in classes
]

# Backgrounds
backgrounds = ["Soldier", "Sage", "Criminal", "Folk Hero"]
background_objs = [
    Background.objects.create(name=bg, created_by=admin_user) for bg in backgrounds
]

# Alignments
alignments = [
    "Lawful Good",
    "Neutral Good",
    "Chaotic Good",
    "Lawful Neutral",
    "True Neutral",
    "Chaotic Neutral",
    "Lawful Evil",
    "Neutral Evil",
    "Chaotic Evil",
]
alignment_objs = [
    Alignment.objects.create(name=al, created_by=admin_user) for al in alignments
]

# Races
races = ["Human", "Elf", "Dwarf", "Halfling"]
race_objs = [Race.objects.create(name=race, created_by=admin_user) for race in races]

# Spells
spells = [
    {
        "name": "Fireball",
        "description": "A bright streak flashes, causing fiery explosion.",
        "level": 3,
        "ritual": False,
        "school": "Evocation",
        "range": "150 feet",
        "duration": "Instantaneous",
    },
    {
        "name": "Mage Hand",
        "description": "A spectral, floating hand appears.",
        "level": 0,
        "ritual": False,
        "school": "Conjuration",
        "range": "30 feet",
        "duration": "1 minute",
    },
]
spell_objs = [Spell.objects.create(created_by=admin_user, **spell) for spell in spells]

# Feats
feats = [
    {"name": "Sharpshooter", "description": "You gain bonuses to ranged attacks."},
    {"name": "Tough", "description": "Your hit point maximum increases."},
]
feat_objs = [Feat.objects.create(created_by=admin_user, **feat) for feat in feats]

# Items
items = [
    {
        "name": "Longsword",
        "weight": 3.0,
        "value": 15.0,
        "weapon": True,
        "attackBonus": 1,
        "damage": "1d8 slashing",
        "description": "A versatile weapon.",
    },
    {
        "name": "Potion of Healing",
        "weight": 0.5,
        "value": 50.0,
        "weapon": False,
        "attackBonus": None,
        "damage": None,
        "description": "Restores hit points.",
    },
]
item_objs = [Item.objects.create(created_by=admin_user, **item) for item in items]

print("Sample data created successfully.")
