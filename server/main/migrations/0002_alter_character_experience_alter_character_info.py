# Generated by Django 5.1.2 on 2024-10-20 17:14

import django.db.models.deletion
from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('main', '0001_initial'),
    ]

    operations = [
        migrations.AlterField(
            model_name='character',
            name='experience',
            field=models.IntegerField(default=0),
        ),
        migrations.AlterField(
            model_name='character',
            name='info',
            field=models.OneToOneField(null=True, on_delete=django.db.models.deletion.PROTECT, to='main.characterinfo'),
        ),
    ]
