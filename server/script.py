from django.utils import timezone
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
from django.contrib.auth.models import User

# Create some example statistics
statistics = [
    Statistic(name="Strength"),
    Statistic(name="Dexterity"),
    Statistic(name="Constitution"),
    Statistic(name="Intelligence"),
    Statistic(name="Wisdom"),
]

# Create some example skills
skills = [
    Skill(name="Acrobatics", statistic=statistics[1]),
    Skill(name="Athletics", statistic=statistics[0]),
    Skill(name="Arcana", statistic=statistics[3]),
    Skill(name="History", statistic=statistics[3]),
    Skill(name="Insight", statistic=statistics[4]),
]

# Create some example classes
classes = [
    Class(name="Wizard", spellcastingAbility=statistics[3]),
    Class(name="Fighter", spellcastingAbility=None),
    Class(name="Rogue", spellcastingAbility=None),
    Class(name="Cleric", spellcastingAbility=statistics[4]),
    Class(name="Druid", spellcastingAbility=statistics[4]),
]

# Create some example backgrounds
backgrounds = [
    Background(name="Soldier"),
    Background(name="Noble"),
    Background(name="Criminal"),
    Background(name="Sailor"),
    Background(name="Hermit"),
]

# Create some example alignments
alignments = [
    Alignment(name="Lawful Good"),
    Alignment(name="Neutral Good"),
    Alignment(name="Chaotic Good"),
    Alignment(name="Lawful Neutral"),
    Alignment(name="Chaotic Neutral"),
]

# Create some example races
races = [
    Race(name="Human"),
    Race(name="Elf"),
    Race(name="Dwarf"),
    Race(name="Halfling"),
    Race(name="Tiefling"),
]

# Create some example spells
spells = [
    Spell(
        name="Magic Missile",
        level=1,
        description="Creates magical darts.",
        ritual=False,
    ),
    Spell(name="Fireball", level=3, description="A fiery explosion.", ritual=False),
    Spell(name="Cure Wounds", level=1, description="Heals a creature.", ritual=False),
    Spell(name="Shield", level=1, description="Protects the caster.", ritual=False),
    Spell(
        name="Teleport",
        level=7,
        description="Instantly transports the caster.",
        ritual=False,
    ),
]

# Create some example feats
feats = [
    Feat(
        name="Great Weapon Master",
        description="You can take a penalty to hit for a bonus to damage.",
    ),
    Feat(
        name="Sharpshooter",
        description="You can ignore cover and take a penalty to hit for more damage.",
    ),
    Feat(name="Tough", description="Increase your hit points."),
    Feat(name="Lucky", description="You can reroll dice."),
    Feat(name="Athlete", description="You are skilled in climbing and jumping."),
]

# Create some example items
items = [
    Item(name="Shield", weight=6.0, value=15.0, weapon=False),
    Item(
        name="Potion of Healing",
        weight=0.5,
        value=50.0,
        weapon=False,
        description="Restores health.",
    ),
    Item(
        name="Bow",
        weight=2.0,
        value=25.0,
        weapon=True,
        attackBonus=4,
        damage="1d6 piercing",
    ),
    Item(
        name="Dagger",
        weight=1.0,
        value=2.0,
        weapon=True,
        attackBonus=3,
        damage="1d4 piercing",
    ),
]


new_item = Item(
    name="Potion of Healing",
    weight=0.5,
    value=50.0,
    weapon=False,
    description="Restores health.",
)

new_item.save()


# # Save all instances
# for stat in statistics:
#     stat.save()

# for skill in skills:
#     skill.save()

# for cl in classes:
#     cl.save()

# for bg in backgrounds:
#     bg.save()

# for alignment in alignments:
#     alignment.save()

# for race in races:
#     race.save()

# for spell in spells:
#     spell.save()

# for feat in feats:
#     feat.save()

for item in items:
    item.save()
