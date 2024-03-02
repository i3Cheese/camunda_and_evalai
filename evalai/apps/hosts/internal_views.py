from django.contrib.auth.models import User

from rest_framework import permissions, status
from rest_framework.decorators import (
    api_view,
    authentication_classes,
    permission_classes,
    throttle_classes,
)
from rest_framework.response import Response
from rest_framework_expiring_authtoken.authentication import (
    ExpiringTokenAuthentication,
)
from rest_framework.throttling import UserRateThrottle
from rest_framework_simplejwt.authentication import JWTAuthentication

from accounts.permissions import IsInternalAccount
from base.utils import get_model_object, team_paginated_queryset
from .filters import HostTeamsFilter
from .models import ChallengeHost, ChallengeHostTeam
from .serializers import (
    ChallengeHostSerializer,
    ChallengeHostTeamSerializer,
    InviteHostToTeamSerializer,
    HostTeamDetailSerializer,
)


@api_view(["POST"])
@permission_classes((permissions.IsAuthenticated, IsInternalAccount))
@authentication_classes((JWTAuthentication, ExpiringTokenAuthentication))
def create_challenge_host_team(request):
    serializer = ChallengeHostTeamSerializer(
        data=request.data, context={"request": request}
    )
    if serializer.is_valid():
        serializer.save()
        response_data = serializer.data
        return Response(response_data, status=status.HTTP_201_CREATED)
    return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
