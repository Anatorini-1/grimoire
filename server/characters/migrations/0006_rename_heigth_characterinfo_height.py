# Generated by Django 5.1.2 on 2024-12-02 13:01

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('characters', '0005_remove_character_classname_alter_character_player_and_more'),
    ]

    operations = [
        migrations.RenameField(
            model_name='characterinfo',
            old_name='heigth',
            new_name='height',
        ),
    ]
