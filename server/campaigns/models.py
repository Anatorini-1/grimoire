from django.db.models import (
    Model,
    PROTECT,
    ForeignKey,
    CharField,
)
from django.contrib.auth.models import User


class Campaign(Model):
    dm = ForeignKey(User, on_delete=PROTECT)
    name = CharField(max_length=1024)
