import { CurrencyPipe, DatePipe, TitleCasePipe } from '@angular/common';
import { Component, OnInit, computed, signal } from '@angular/core';
import { ActivatedRoute, RouterLink, RouterLinkActive } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { AppointmentResponse, PatientResponse, ReportSummaryResponse } from '../../core/models/hms.models';
import { AuthService } from '../../core/services/auth.service';
import { HmsApiService } from '../../core/services/hms-api.service';
import { AppointmentCalendarComponent } from '../../shared/appointment-calendar/appointment-calendar.component';
import { DataTableWrapperComponent, TableColumn } from '../../shared/data-table-wrapper/data-table-wrapper.component';
import { LabReportViewerComponent } from '../../shared/lab-report-viewer/lab-report-viewer.component';
import { PatientSearchBarComponent } from '../../shared/patient-search-bar/patient-search-bar.component';
import { PrescriptionViewerComponent } from '../../shared/prescription-viewer/prescription-viewer.component';
import { StatusChipComponent } from '../../shared/status-chip/status-chip.component';
import { VitalsFormComponent } from '../../shared/vitals-form/vitals-form.component';

interface MetricCard {
  label: string;
  value: string | number;
  icon: string;
}

@Component({
  selector: 'app-role-dashboard',
  standalone: true,
  imports: [
    CurrencyPipe,
    DatePipe,
    TitleCasePipe,
    RouterLink,
    RouterLinkActive,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatListModule,
    MatProgressBarModule,
    MatSidenavModule,
    MatSnackBarModule,
    MatTabsModule,
    MatToolbarModule,
    AppointmentCalendarComponent,
    DataTableWrapperComponent,
    LabReportViewerComponent,
    PatientSearchBarComponent,
    PrescriptionViewerComponent,
    StatusChipComponent,
    VitalsFormComponent,
  ],
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
            <div class="toolbar-title">{{ title() }}</div>
            <div class="toolbar-subtitle">{{ auth.currentUser()?.fullName }} · {{ auth.currentUser()?.role | titlecase }}</div>
          </div>
          <span class="spacer"></span>
          <button mat-stroked-button (click)="auth.logout()"><mat-icon>logout</mat-icon> Logout</button>
        </mat-toolbar>

        @if (loading()) {
          <mat-progress-bar mode="indeterminate" />
        }

        <main class="content">
          <section class="metrics">
            @for (metric of metrics(); track metric.label) {
              <mat-card appearance="outlined">
                <mat-card-content>
                  <mat-icon>{{ metric.icon }}</mat-icon>
                  <div>
                    <div class="metric-value">{{ metric.value }}</div>
                    <div class="text-muted">{{ metric.label }}</div>
                  </div>
                </mat-card-content>
              </mat-card>
            }
          </section>

          <mat-tab-group animationDuration="150ms">
            <mat-tab label="Operations">
              <div class="tab-grid">
                <mat-card appearance="outlined">
                  <mat-card-header><mat-card-title>Walk-in Queue</mat-card-title></mat-card-header>
                  <mat-card-content><app-appointment-calendar [appointments]="appointments()" /></mat-card-content>
                </mat-card>
                <mat-card appearance="outlined">
                  <mat-card-header><mat-card-title>Patient Search</mat-card-title></mat-card-header>
                  <mat-card-content>
                    <app-patient-search-bar (search)="filterPatients($event)" />
                    <app-data-table-wrapper [rows]="visiblePatients()" [columns]="patientColumns" />
                  </mat-card-content>
                </mat-card>
              </div>
            </mat-tab>
            <mat-tab label="Clinical">
              <div class="tab-grid">
                <mat-card appearance="outlined">
                  <mat-card-header><mat-card-title>Vitals</mat-card-title></mat-card-header>
                  <mat-card-content><app-vitals-form (save)="snack('Vitals staged for API submit')" /></mat-card-content>
                </mat-card>
                <mat-card appearance="outlined">
                  <mat-card-header><mat-card-title>Prescription</mat-card-title></mat-card-header>
                  <mat-card-content><app-prescription-viewer [medicines]="['Paracetamol 500mg · 1-0-1 · 5 days', 'Amoxicillin 500mg · 1-1-1 · 3 days']" /></mat-card-content>
                </mat-card>
              </div>
            </mat-tab>
            <mat-tab label="Reports">
              <div class="tab-grid">
                <mat-card appearance="outlined">
                  <mat-card-header><mat-card-title>Revenue</mat-card-title></mat-card-header>
                  <mat-card-content>
                    <p class="display-6">{{ summary()?.totalRevenue ?? 0 | currency:'INR' }}</p>
                    <p class="text-muted">Outstanding: {{ summary()?.outstandingDues ?? 0 | currency:'INR' }}</p>
                  </mat-card-content>
                </mat-card>
                <mat-card appearance="outlined">
                  <mat-card-header><mat-card-title>Lab Reports</mat-card-title></mat-card-header>
                  <mat-card-content><app-lab-report-viewer [reports]="[{ title: 'CBC', summary: 'Awaiting connected lab order selection.' }]" /></mat-card-content>
                </mat-card>
              </div>
            </mat-tab>
          </mat-tab-group>
        </main>
      </mat-sidenav-content>
    </mat-sidenav-container>
  `,
  styles: [`
    .app-frame { min-height: 100vh; background: #f5f7f8; }
    .sidebar { width: 260px; border-right: 1px solid #dfe5e8; }
    .brand { height: 64px; display: flex; align-items: center; gap: .75rem; padding: 0 1rem; font-size: 1.25rem; }
    .active-link { background: #e7f1ee; }
    .topbar { position: sticky; top: 0; z-index: 2; background: white; border-bottom: 1px solid #dfe5e8; }
    .toolbar-title { font-weight: 800; line-height: 1.1; }
    .toolbar-subtitle { font-size: .8rem; color: #667085; }
    .spacer { flex: 1; }
    .content { padding: 1.25rem; display: grid; gap: 1.25rem; }
    .metrics { display: grid; grid-template-columns: repeat(auto-fit, minmax(190px, 1fr)); gap: 1rem; }
    .metrics mat-card-content { display: flex; gap: 1rem; align-items: center; }
    .metrics mat-icon { color: #0f766e; }
    .metric-value { font-size: 1.65rem; font-weight: 800; }
    .tab-grid { padding-top: 1rem; display: grid; grid-template-columns: repeat(auto-fit, minmax(320px, 1fr)); gap: 1rem; }
    mat-card { border-radius: 8px; }
    @media (max-width: 900px) { .sidebar { display: none; } .content { padding: .75rem; } }
  `],
})
export class RoleDashboardComponent implements OnInit {
  readonly title = signal('Dashboard');
  readonly loading = signal(false);
  readonly summary = signal<ReportSummaryResponse | null>(null);
  readonly patients = signal<PatientResponse[]>([]);
  readonly query = signal('');
  readonly appointments = signal<AppointmentResponse[]>([]);
  readonly visiblePatients = computed(() => {
    const query = this.query().toLowerCase();
    return this.patients().filter((patient) => patient.fullName.toLowerCase().includes(query) || patient.mrn.toLowerCase().includes(query));
  });
  readonly metrics = computed<MetricCard[]>(() => {
    const summary = this.summary();
    return [
      { label: 'Patients', value: summary?.totalPatients ?? this.patients().length, icon: 'groups' },
      { label: 'Doctors', value: summary?.totalDoctors ?? '—', icon: 'stethoscope' },
      { label: 'Today Appointments', value: summary?.todayAppointments ?? this.appointments().length, icon: 'event_available' },
      { label: 'Active Admissions', value: summary?.activeAdmissions ?? '—', icon: 'bed' },
    ];
  });
  readonly patientColumns: TableColumn<PatientResponse>[] = [
    { key: 'mrn', label: 'MRN' },
    { key: 'fullName', label: 'Patient' },
    { key: 'gender', label: 'Gender' },
    { key: 'phone', label: 'Phone' },
  ];
  readonly navItems = [
    { path: '/receptionist', label: 'Reception', icon: 'support_agent' },
    { path: '/doctor', label: 'Doctor', icon: 'clinical_notes' },
    { path: '/nurse', label: 'Nurse', icon: 'monitor_heart' },
    { path: '/lab', label: 'Lab', icon: 'biotech' },
    { path: '/pharmacist', label: 'Pharmacy', icon: 'medication' },
    { path: '/admin', label: 'Admin', icon: 'admin_panel_settings' },
    { path: '/patient-portal', label: 'Patient Portal', icon: 'person' },
  ];

  constructor(
    readonly auth: AuthService,
    private readonly route: ActivatedRoute,
    private readonly api: HmsApiService,
    private readonly snackBar: MatSnackBar,
  ) {}

  ngOnInit(): void {
    this.title.set(String(this.route.snapshot.data['title'] ?? 'Dashboard'));
    this.load();
  }

  filterPatients(query: string): void {
    this.query.set(query);
  }

  snack(message: string): void {
    this.snackBar.open(message, 'Close', { duration: 2500 });
  }

  private load(): void {
    this.loading.set(true);
    this.api.patients().subscribe({ next: (patients) => this.patients.set(patients), error: () => this.snack('Patient API unavailable') });
    this.api.appointments().subscribe({ next: (appointments) => this.appointments.set(appointments), error: () => this.snack('Appointment API unavailable') });
    this.api.reportSummary().subscribe({
      next: (summary) => this.summary.set(summary),
      error: () => this.snack('Reports require an admin token'),
      complete: () => this.loading.set(false),
    });
  }
}
