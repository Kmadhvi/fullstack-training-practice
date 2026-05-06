import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { UserRole } from '../models/hms.models';

export const roleGuard: CanActivateFn = (route) => {
  const auth = inject(AuthService);
  const router = inject(Router);
  const roles = (route.data['roles'] ?? []) as UserRole[];
  if (auth.hasRole(roles)) {
    return true;
  }
  const user = auth.currentUser();
  return router.createUrlTree([user ? auth.dashboardPath(user.role) : '/login']);
};
