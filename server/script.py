from main.models import Spell


Spell(
    name="Magic Missile",
    description="A missile of magical energy strikes a target",
    level=1,
    ritual=False,
).save()
Spell(
    name="Identify",
    description="You choose one object you must touch, learning its properties",
    level=1,
    ritual=True,
).save()
Spell(
    name="Fireball",
    description="A bright streak flashes to a point you choose within range, exploding into flame",
    level=3,
    ritual=False,
).save()
Spell(
    name="Detect Magic",
    description="For the duration, you sense the presence of magic within 30 feet",
    level=1,
    ritual=True,
).save()
Spell(
    name="Mage Hand",
    description="A spectral floating hand appears within range and performs tasks",
    level=0,
    ritual=False,
).save()
Spell(
    name="Shield",
    description="An invisible barrier of magical force appears and protects you",
    level=1,
    ritual=False,
).save()
Spell(
    name="Healing Word",
    description="A creature you can see within range regains hit points",
    level=1,
    ritual=False,
).save()
Spell(
    name="Invisibility",
    description="A creature you touch becomes invisible",
    level=2,
    ritual=False,
).save()
Spell(
    name="Feather Fall",
    description="Choose up to five falling creatures within range, slowing their fall",
    level=1,
    ritual=True,
).save()
Spell(
    name="Water Walk",
    description="This spell grants the ability to move across any liquid surface",
    level=3,
    ritual=True,
).save()
Spell(
    name="Teleport",
    description="Instantly transports you and up to eight willing creatures to a known location",
    level=7,
    ritual=False,
).save()
