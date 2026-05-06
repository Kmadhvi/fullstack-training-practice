import { computed, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { map, Observable, tap } from 'rxjs';
import { ApiResponse, AuthResponse, LoginRequest, UserRole } from '../models/hms.models';

const TOKEN_KEY = 'hms_token';
const USER_KEY = 'hms_user';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly apiUrl = 'http://127.0.0.1:8080/api/auth';
  private readonly currentUserSignal = signal<AuthResponse | null>(this.readUser());

  readonly currentUser = this.currentUserSignal.asReadonly();
  readonly isAuthenticated = computed(() => Boolean(this.currentUserSignal()?.token));

  constructor(
    private readonly http: HttpClient,
    private readonly router: Router,
  ) {}

  login(request: LoginRequest): Observable<AuthResponse> {
    return this.http.post<ApiResponse<AuthResponse>>(`${this.apiUrl}/login`, request).pipe(
      map((response) => response.data),
      tap((auth) => {
        localStorage.setItem(TOKEN_KEY, auth.token);
        localStorage.setItem(USER_KEY, JSON.stringify(auth));
        this.currentUserSignal.set(auth);
      }),
    );
  }

  logout(): void {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USER_KEY);
    this.currentUserSignal.set(null);
    void this.router.navigateByUrl('/login');
  }

  token(): string | null {
    return localStorage.getItem(TOKEN_KEY);
  }

  hasRole(roles: readonly UserRole[]): boolean {
    const user = this.currentUserSignal();
    return Boolean(user && roles.includes(user.role));
  }

  dashboardPath(role: UserRole): string {
    const paths: Record<UserRole, string> = {
      ROLE_ADMIN: '/admin',
      ROLE_DOCTOR: '/doctor',
      ROLE_NURSE: '/nurse',
      ROLE_RECEPTIONIST: '/receptionist',
      ROLE_LAB_TECH: '/lab',
      ROLE_PHARMACIST: '/pharmacist',
      ROLE_PATIENT: '/patient-portal',
    };
    return paths[role];
  }

  private readUser(): AuthResponse | null {
    const raw = localStorage.getItem(USER_KEY);
    if (!raw) {
      return null;
    }
    try {
      return JSON.parse(raw) as AuthResponse;
    } catch {
      localStorage.removeItem(USER_KEY);
      localStorage.removeItem(TOKEN_KEY);
      return null;
    }
  }
}
