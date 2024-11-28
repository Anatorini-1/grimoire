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

from characters.models import Character


class Campaign(Model):
    dm = ForeignKey(User, on_delete=PROTECT)
    name = CharField(max_length=1024)


class CampaignPlayer(Model):
    campaign = ForeignKey(
        Campaign, on_delete=CASCADE, null=False, related_name="players"
    )
    player = ForeignKey(User, on_delete=CASCADE, null=False, related_name="campaigns")
    character = ForeignKey(Character, on_delete=PROTECT, null=True, default=None)
    accepted = BooleanField(default=False)


class CampaignChatMessage(Model):
    campaign = ForeignKey(
        Campaign, on_delete=CASCADE, null=False, related_name="messages"
    )
    sender = ForeignKey(User, on_delete=CASCADE, null=False)
    message = CharField(max_length=256, null=False)
    created_at = DateTimeField(auto_now_add=True)

    def save(self, *args, **kwargs):
        if not self.id:  # type: ignore
            self.created_at = timezone.now()
        return super().save(*args, **kwargs)
