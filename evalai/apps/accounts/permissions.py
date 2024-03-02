from allauth.account.models import EmailAddress
from rest_framework import permissions


class HasVerifiedEmail(permissions.BasePermission):
    """
    Permission class for if the user has verified the email or not
    """

    message = "Please verify your email!"

    def has_permission(self, request, view):

        if request.user.is_anonymous:
            return True
        else:
            if EmailAddress.objects.filter(
                user=request.user, verified=True
            ).exists():
                return True
            else:
                return False


class IsInternalAccount(permissions.BasePermission):
    """
    Permission class for if the user are from our own services.
    """

    message = "You are not allowed to be here."

    def has_permission(self, request, view):
        return request.user.is_superuser
