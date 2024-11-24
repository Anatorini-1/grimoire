from main.views import (
    UserViewSet,
    GroupViewSet,
    TestView,
)
from rest_framework import routers
from library.views import (
    ClassViewSet,
    StatisticViewSet,
    ItemViewSet,
    SpellViewSet,
    RaceViewSet,
    AlignmentViewSet,
    BackgroundViewSet,
)
from campaigns.views import CampaignViewSet
from characters.views import CharacterViewSet

from django.contrib import admin
from django.urls import path, include

router = routers.DefaultRouter()
router.register(r"users", UserViewSet)
router.register(r"groups", GroupViewSet)


router.register(r"campaigns", CampaignViewSet)
router.register(r"classes", ClassViewSet)
router.register(r"characters", CharacterViewSet)
router.register(r"items", ItemViewSet)
router.register(r"spells", SpellViewSet)
router.register(r"races", RaceViewSet)
router.register(r"alignments", AlignmentViewSet)
router.register(r"backgrounds", BackgroundViewSet)
router.register(r"statistics", StatisticViewSet)

urlpatterns = [
    path("", include(router.urls)),
    path("api-auth/", include("rest_framework.urls", namespace="rest_framework")),
    path("admin/", admin.site.urls),
    path("test/", TestView.as_view()),
]
