import { TitleCasePipe } from '@angular/common';
import { Component, Input } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-dashboard-shell',
  standalone: true,
  imports: [TitleCasePipe, RouterLink, RouterLinkActive, MatButtonModule, MatIconModule, MatListModule, MatSidenavModule, MatToolbarModule],
  template: `
    <mat-sidenav-container class="app-frame">
      <mat-sidenav mode="side" opened class="sidebar">
        <div class="brand">
          <mat-icon>local_hospital</mat-icon>
          <strong>HMS</strong>
        </div>
        <mat-nav-list>
          @for (item of navItems; track item.path) {
            <a mat-list-item [routerLink]="item.path" routerLinkActive="active-link">
              <mat-icon matListItemIcon>{{ item.icon }}</mat-icon>
              <span matListItemTitle>{{ item.label }}</span>
            </a>
          }
        </mat-nav-list>
      </mat-sidenav>

      <mat-sidenav-content>
        <mat-toolbar class="topbar">
          <div>
            <div class="toolbar-title">{{ title }}</div>
            <div class="toolbar-subtitle">{{ auth.currentUser()?.fullName }} | {{ auth.currentUser()?.role | titlecase }}</div>
          </div>
          <span class="spacer"></span>
          <button mat-stroked-button type="button" (click)="auth.logout()">
            <mat-icon>logout</mat-icon>
            Logout
          </button>
        </mat-toolbar>

        <main class="content">
          <ng-content />
        </main>
      </mat-sidenav-content>
    </mat-sidenav-container>
  `,
  styles: [`
    .app-frame { min-height: 100vh; background: #f4f7f8; }
    .sidebar { width: 260px; border-right: 1px solid #dfe5e8; }
    .brand { height: 64px; display: flex; align-items: center; gap: .75rem; padding: 0 1rem; font-size: 1.25rem; }
    .brand mat-icon { color: #0f766e; }
    .active-link { background: #e7f1ee; }
    .topbar { position: sticky; top: 0; z-index: 3; background: #fff; border-bottom: 1px solid #dfe5e8; }
    .toolbar-title { font-weight: 800; line-height: 1.1; }
    .toolbar-subtitle { font-size: .8rem; color: #667085; }
    .spacer { flex: 1; }
    .content { padding: 1.25rem; display: grid; gap: 1.25rem; }
    @media (max-width: 900px) {
      .sidebar { display: none; }
      .content { padding: .75rem; }
      .topbar { height: auto; min-height: 64px; align-items: center; }
    }
  `],
})
export class DashboardShellComponent {
  @Input({ required: true }) title = 'Dashboard';

  readonly navItems = [
    { path: '/receptionist', label: 'Reception', icon: 'support_agent' },
    { path: '/doctor', label: 'Doctor', icon: 'clinical_notes' },
    { path: '/nurse', label: 'Nurse', icon: 'monitor_heart' },
    { path: '/lab', label: 'Lab', icon: 'biotech' },
    { path: '/pharmacist', label: 'Pharmacy', icon: 'medication' },
    { path: '/admin', label: 'Admin', icon: 'admin_panel_settings' },
    { path: '/patient-portal', label: 'Patient', icon: 'person' },
  ];

  constructor(readonly auth: AuthService) {}
}
