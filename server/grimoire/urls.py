from main.views import (
    TestView,
)
from users.views import RegisterView, UserViewSet
from rest_framework import routers
from rest_framework.authtoken import views
from library.views import (
    ClassViewSet,
    FeatViewSet,
    SkillViewSet,
    StatisticViewSet,
    ItemViewSet,
    SpellViewSet,
    RaceViewSet,
    AlignmentViewSet,
    BackgroundViewSet,
)
from campaigns.views import CampaignViewSet
from characters.views import CharacterViewSet
from users.views import UserLoginView

from django.contrib import admin
from django.urls import path, include

router = routers.DefaultRouter()
router.register(r"users", UserViewSet)
router.register(r"campaigns", CampaignViewSet)
router.register(r"classes", ClassViewSet)
router.register(r"characters", CharacterViewSet)
router.register(r"items", ItemViewSet)
router.register(r"spells", SpellViewSet)
router.register(r"races", RaceViewSet)
router.register(r"alignments", AlignmentViewSet)
router.register(r"backgrounds", BackgroundViewSet)
router.register(r"statistics", StatisticViewSet)
router.register(r"feats", FeatViewSet)
router.register(r"skills", SkillViewSet)


urlpatterns = [
    path("", include(router.urls)),
    path("api-auth/", include("rest_framework.urls", namespace="rest_framework")),
    path("api-token-auth/", views.obtain_auth_token),
    path("login/", UserLoginView.as_view()),
    path("register/", RegisterView.as_view()),
    path("admin/", admin.site.urls),
    path("test/", TestView.as_view()),
]
