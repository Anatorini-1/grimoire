# Generated by Django 5.1.2 on 2024-10-15 18:39

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('main', '0003_rename_statisctics_statistics'),
    ]

    operations = [
        migrations.AddField(
            model_name='statistics',
            name='charisma',
            field=models.IntegerField(default=0),
        ),
        migrations.AddField(
            model_name='statistics',
            name='constitution',
            field=models.IntegerField(default=0),
        ),
        migrations.AddField(
            model_name='statistics',
            name='dexterity',
            field=models.IntegerField(default=0),
        ),
        migrations.AddField(
            model_name='statistics',
            name='intelligence',
            field=models.IntegerField(default=0),
        ),
        migrations.AddField(
            model_name='statistics',
            name='strength',
            field=models.IntegerField(default=0),
        ),
        migrations.AddField(
            model_name='statistics',
            name='wisdom',
            field=models.IntegerField(default=0),
        ),
    ]