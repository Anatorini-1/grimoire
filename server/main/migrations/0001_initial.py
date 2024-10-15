# Generated by Django 5.1.2 on 2024-10-20 16:25

import django.db.models.deletion
from django.conf import settings
from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
    ]

    operations = [
        migrations.CreateModel(
            name='Alignment',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=64, unique=True)),
            ],
        ),
        migrations.CreateModel(
            name='Background',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=64, unique=True)),
            ],
        ),
        migrations.CreateModel(
            name='CasterInfo',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
            ],
        ),
        migrations.CreateModel(
            name='CharacterInfo',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('age', models.IntegerField()),
                ('heigth', models.IntegerField()),
                ('weight', models.IntegerField()),
                ('eyes', models.CharField(max_length=16)),
                ('skin', models.CharField(max_length=32)),
                ('hair', models.CharField(max_length=64)),
                ('allies_and_orgs', models.TextField()),
                ('apperance', models.TextField()),
                ('backstory', models.TextField()),
                ('treasure', models.TextField()),
                ('additional_features_and_traits', models.TextField()),
            ],
        ),
        migrations.CreateModel(
            name='Class',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=64, unique=True)),
            ],
        ),
        migrations.CreateModel(
            name='Feat',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=32)),
                ('description', models.TextField()),
            ],
        ),
        migrations.CreateModel(
            name='Item',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=256, unique=True)),
                ('weight', models.DecimalField(decimal_places=2, max_digits=10)),
                ('value', models.DecimalField(decimal_places=2, max_digits=10)),
                ('weapon', models.BooleanField(default=False)),
                ('attack_bonus', models.IntegerField(null=True)),
                ('damage', models.CharField(max_length=128, null=True)),
                ('description', models.TextField(null=True)),
            ],
        ),
        migrations.CreateModel(
            name='Race',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=64, unique=True)),
            ],
        ),
        migrations.CreateModel(
            name='Skill',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=64)),
            ],
        ),
        migrations.CreateModel(
            name='Spell',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=256, unique=True)),
                ('description', models.TextField(null=True)),
                ('level', models.IntegerField()),
                ('ritual', models.BooleanField(default=False)),
            ],
        ),
        migrations.CreateModel(
            name='Statistic',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=32)),
            ],
        ),
        migrations.CreateModel(
            name='Campaign',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=1024)),
                ('dm', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, to=settings.AUTH_USER_MODEL)),
            ],
        ),
        migrations.CreateModel(
            name='Character',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=128)),
                ('experience', models.IntegerField()),
                ('death_save_success', models.IntegerField(default=0)),
                ('death_save_failure', models.IntegerField(default=0)),
                ('temporary_hitpoint', models.IntegerField(default=0)),
                ('alignment', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, to='main.alignment')),
                ('background', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, to='main.background')),
                ('campaign', models.ForeignKey(null=True, on_delete=django.db.models.deletion.CASCADE, to='main.campaign')),
                ('caster_info', models.OneToOneField(null=True, on_delete=django.db.models.deletion.PROTECT, to='main.casterinfo')),
                ('player', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, related_name='player', to=settings.AUTH_USER_MODEL)),
                ('info', models.OneToOneField(on_delete=django.db.models.deletion.PROTECT, to='main.characterinfo')),
                ('classname', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, related_name='classname', to='main.class')),
                ('race', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, to='main.race')),
            ],
        ),
        migrations.CreateModel(
            name='CharacterEquipment',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('cp', models.IntegerField()),
                ('sp', models.IntegerField()),
                ('ep', models.IntegerField()),
                ('gp', models.IntegerField()),
                ('pp', models.IntegerField()),
                ('character', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='main.character')),
            ],
        ),
        migrations.CreateModel(
            name='CharacterStatistic',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('value', models.IntegerField()),
                ('character', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='main.character')),
            ],
        ),
        migrations.AddField(
            model_name='casterinfo',
            name='spellcasting_class',
            field=models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, to='main.class'),
        ),
        migrations.CreateModel(
            name='CharacterItem',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('quantity', models.IntegerField()),
                ('character', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='main.character')),
                ('item', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, to='main.item')),
            ],
        ),
        migrations.CreateModel(
            name='CharacterSkill',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('proficiency', models.BooleanField(default=False)),
                ('expertise', models.BooleanField(default=False)),
                ('character', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='main.character')),
                ('skill', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, to='main.skill')),
            ],
        ),
        migrations.CreateModel(
            name='CharacterSpells',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('prepared', models.BooleanField(default=False)),
                ('character', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='main.character')),
                ('spell', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='main.spell')),
            ],
        ),
        migrations.CreateModel(
            name='SpellSlotsExpended',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('level', models.IntegerField()),
                ('count', models.IntegerField()),
                ('spell_caster_info', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='main.casterinfo')),
            ],
        ),
        migrations.AddField(
            model_name='skill',
            name='statistic',
            field=models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, to='main.statistic'),
        ),
        migrations.AddField(
            model_name='class',
            name='spellcasting_ability',
            field=models.ForeignKey(null=True, on_delete=django.db.models.deletion.PROTECT, to='main.statistic'),
        ),
    ]
