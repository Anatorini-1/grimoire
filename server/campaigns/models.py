from django.db.models import (
    Model,
    PROTECT,
    ForeignKey,
    CharField,
    CASCADE,
    BooleanField,
    DateTimeField,
)
from django.contrib.auth.models import User
from django.utils import timezone

from characters.models import NewCharacter


class Campaign(Model):
    dm = ForeignKey(User, on_delete=PROTECT)
    name = CharField(max_length=1024)


class CampaignPlayer(Model):
    campaign = ForeignKey(
        Campaign, on_delete=CASCADE, null=False, related_name="players"
    )
    player = ForeignKey(User, on_delete=CASCADE, null=False, related_name="campaigns")
    character = ForeignKey(NewCharacter, on_delete=PROTECT, null=True, default=None)
    accepted = BooleanField(default=False)
