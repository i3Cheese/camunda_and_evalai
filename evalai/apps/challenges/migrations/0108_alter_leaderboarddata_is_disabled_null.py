# Generated by Django 2.2.20 on 2023-11-07 06:30

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('challenges', '0107_leaderboarddata_is_disabled'),
    ]

    operations = [
        migrations.AlterField(
            model_name='leaderboarddata',
            name='is_disabled',
            field=models.BooleanField(default=False, null=True),
        ),
    ]
