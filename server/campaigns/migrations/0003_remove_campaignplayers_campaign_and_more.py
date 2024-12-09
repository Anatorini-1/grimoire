# Generated by Django 5.1.2 on 2024-11-26 18:27

import django.db.models.deletion
from django.conf import settings
from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('campaigns', '0002_campaignchatmessages_campaignplayers'),
        ('characters', '0003_alter_characterequipment_character_and_more'),
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
    ]

    operations = [
        migrations.RemoveField(
            model_name='campaignplayers',
            name='campaign',
        ),
        migrations.RemoveField(
            model_name='campaignplayers',
            name='character',
        ),
        migrations.RemoveField(
            model_name='campaignplayers',
            name='player',
        ),
        migrations.CreateModel(
            name='CampaignChatMessage',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('message', models.CharField(max_length=256)),
                ('campaign', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='messages', to='campaigns.campaign')),
                ('sender', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to=settings.AUTH_USER_MODEL)),
            ],
        ),
        migrations.CreateModel(
            name='CampaignPlayer',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('accepted', models.BooleanField(default=False)),
                ('campaign', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='players', to='campaigns.campaign')),
                ('character', models.ForeignKey(default=None, null=True, on_delete=django.db.models.deletion.PROTECT, to='characters.character')),
                ('player', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='campaigns', to=settings.AUTH_USER_MODEL)),
            ],
        ),
        migrations.DeleteModel(
            name='CampaignChatMessages',
        ),
        migrations.DeleteModel(
            name='CampaignPlayers',
        ),
    ]