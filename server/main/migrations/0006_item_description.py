# Generated by Django 5.1.2 on 2024-10-15 19:18

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('main', '0005_rename_statisctics_character_statistics'),
    ]

    operations = [
        migrations.AddField(
            model_name='item',
            name='description',
            field=models.TextField(null=True),
        ),
    ]
