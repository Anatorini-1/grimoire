from django.urls import re_path

from .consumers.chat_consumer import ChatConsumer

ws_urlpatterns = [
    re_path(r"ws/session/(?P<session_name>[A-Za-z0-9_-]+)/$", ChatConsumer.as_asgi())
]
