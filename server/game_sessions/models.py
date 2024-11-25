from django.db import models

from campaigns.models import Campaign

# Create your models here.


class Session(models.Model):
    campaign = models.ForeignKey(Campaign, on_delete=models.CASCADE)
    active = models.BooleanField()
    created_at = models.DateTimeField()
    ended_at = models.DateTimeField()


class CombatSession(models.Model):
    session = models.ForeignKey(Session, on_delete=models.CASCADE)


class CombatSessionLog(models.Model):
    combat_session = models.ForeignKey(CombatSession, on_delete=models.CASCADE)
    log = models.CharField(max_length=256)
