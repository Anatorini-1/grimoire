# Generated by Django 5.1.2 on 2024-11-28 19:53

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('game_sessions', '0003_alter_campaignsession_ended_at'),
    ]

    operations = [
        migrations.AddField(
            model_name='campaignsession',
            name='token',
            field=models.CharField(max_length=40, null=True),
        ),
    ]
