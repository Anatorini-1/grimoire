"""
ASGI config for grimoire project.

It exposes the ASGI callable as a module-level variable named ``application``.

For more information on this file, see
https://docs.djangoproject.com/en/5.1/howto/deployment/asgi/
"""

import os

from django.core.asgi import get_asgi_application
from channels.routing import ProtocolTypeRouter, URLRouter
from django.urls import path
from game_sessions.consumers.chat_consumer import ChatConsumer
from game_sessions import routing
from channels.auth import AuthMiddlewareStack
from rest_framework.authtoken.models import Token
from django.contrib.auth.models import AnonymousUser

from grimoire.middleware import TokenAuthMiddleware


os.environ.setdefault("DJANGO_SETTINGS_MODULE", "grimoire.settings")

application = get_asgi_application()

application = ProtocolTypeRouter(
    {
        "http": get_asgi_application(),
        "websocket": TokenAuthMiddleware(URLRouter(routing.ws_urlpatterns)),
    }
)
