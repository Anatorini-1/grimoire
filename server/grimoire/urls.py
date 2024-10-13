from main.views import TestView, UserViewSet,GroupViewSet, SpellViewSet
from rest_framework import routers

from django.contrib import admin
from django.urls import path, include

router = routers.DefaultRouter()
router.register(r"users", UserViewSet)
router.register(r"groups", GroupViewSet)
router.register(r"spells", SpellViewSet)

urlpatterns = [
    path("", include(router.urls)),
    path('api-auth/', include('rest_framework.urls', namespace='rest_framework')),
    path('admin/', admin.site.urls),
    path('test/', TestView.as_view())
]
