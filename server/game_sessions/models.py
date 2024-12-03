from django.db import models

from campaigns.models import Campaign, CampaignPlayer
from django.utils import timezone
# Create your models here.


class CampaignSession(models.Model):
    campaign = models.ForeignKey(
        Campaign, on_delete=models.CASCADE, related_name="sessions"
    )
    active = models.BooleanField()
    created_at = models.DateTimeField()
    ended_at = models.DateTimeField(null=True, default=None)
    token = models.CharField(max_length=80, null=True)

    def save(self, *args, **kwargs):
        if not self.id:  # type: ignore
            self.created_at = timezone.now()
        return super().save(*args, **kwargs)


class CampaignSessionConnectedPlayers(models.Model):
    player = models.ForeignKey(
        CampaignPlayer, on_delete=models.CASCADE, related_name="connected_sessions"
    )
    session = models.ForeignKey(
        CampaignSession, on_delete=models.CASCADE, related_name="connected_players"
    )


class CombatSession(models.Model):
    session = models.ForeignKey(CampaignSession, on_delete=models.CASCADE)


class CombatSessionLog(models.Model):
    combat_session = models.ForeignKey(CombatSession, on_delete=models.CASCADE)
    log = models.CharField(max_length=256)


class SessionMessage(models.Model):
    session = models.ForeignKey(
        CampaignSession, on_delete=models.CASCADE, related_name="messages"
    )
    sender = models.ForeignKey(CampaignPlayer, on_delete=models.CASCADE, null=False)
    message = models.CharField(max_length=256, null=False)
    created_at = models.DateTimeField(auto_now_add=True)

    def save(self, *args, **kwargs):
        if not self.id:  # type: ignore
            self.created_at = timezone.now()
        return super().save(*args, **kwargs)
