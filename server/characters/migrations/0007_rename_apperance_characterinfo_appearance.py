# Generated by Django 5.1.2 on 2024-12-02 13:01

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('characters', '0006_rename_heigth_characterinfo_height'),
    ]

    operations = [
        migrations.RenameField(
            model_name='characterinfo',
            old_name='apperance',
            new_name='appearance',
        ),
    ]