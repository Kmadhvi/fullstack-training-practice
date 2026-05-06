import { Component, OnInit, computed, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTabsModule } from '@angular/material/tabs';
import { AppointmentResponse, PatientResponse } from '../../core/models/hms.models';
import { HmsApiService } from '../../core/services/hms-api.service';
import { AppointmentCalendarComponent } from '../../shared/appointment-calendar/appointment-calendar.component';
import { DataTableWrapperComponent, TableColumn } from '../../shared/data-table-wrapper/data-table-wrapper.component';
import { PatientSearchBarComponent } from '../../shared/patient-search-bar/patient-search-bar.component';
import { StatusChipComponent } from '../../shared/status-chip/status-chip.component';
import { DashboardShellComponent } from '../dashboard/dashboard-shell.component';
import { MetricCard, QuickAction } from '../dashboard/dashboard-ui';

@Component({
  selector: 'app-receptionist-dashboard',
  standalone: true,
  imports: [MatButtonModule, MatCardModule, MatIconModule, MatSnackBarModule, MatTabsModule, AppointmentCalendarComponent, DataTableWrapperComponent, PatientSearchBarComponent, StatusChipComponent, DashboardShellComponent],
  template: `
    <app-dashboard-shell title="Reception Desk">
      <section class="hero">
        <div>
          <h1>Front desk control center</h1>
          <p>Register patients, manage OPD flow, handle walk-ins, and keep billing handoffs visible.</p>
        </div>
        <div class="hero-actions">
          @for (action of actions; track action.label) {
            <button mat-flat-button color="primary" type="button" (click)="notify(action.label)">
              <mat-icon>{{ action.icon }}</mat-icon>
              {{ action.label }}
            </button>
          }
        </div>
      </section>

      <section class="metrics">
        @for (metric of metrics(); track metric.label) {
          <mat-card appearance="outlined"><mat-card-content><mat-icon>{{ metric.icon }}</mat-icon><div><strong>{{ metric.value }}</strong><span>{{ metric.label }}</span></div></mat-card-content></mat-card>
        }
      </section>

      <mat-tab-group>
        <mat-tab label="Queue">
          <div class="grid">
            <mat-card appearance="outlined">
              <mat-card-header><mat-card-title>Walk-in Queue</mat-card-title></mat-card-header>
              <mat-card-content><app-appointment-calendar [appointments]="appointments()" /></mat-card-content>
            </mat-card>
            <mat-card appearance="outlined">
              <mat-card-header><mat-card-title>Appointment Handoffs</mat-card-title></mat-card-header>
              <mat-card-content class="work-list">
                @for (item of handoffs; track item.title) {
                  <div class="work-row"><div><strong>{{ item.title }}</strong><p>{{ item.subtitle }}</p></div><app-status-chip [label]="item.status" /></div>
                }
              </mat-card-content>
            </mat-card>
          </div>
        </mat-tab>
        <mat-tab label="Patients">
          <mat-card appearance="outlined">
            <mat-card-header><mat-card-title>Patient Registry</mat-card-title></mat-card-header>
            <mat-card-content>
              <app-patient-search-bar (search)="query.set($event)" />
              <app-data-table-wrapper [rows]="visiblePatients()" [columns]="patientColumns" />
            </mat-card-content>
          </mat-card>
        </mat-tab>
      </mat-tab-group>
    </app-dashboard-shell>
  `,
  styles: [`
    .hero { display: flex; justify-content: space-between; gap: 1rem; align-items: center; padding: 1.25rem; background: #fff; border: 1px solid #dfe5e8; border-radius: 8px; }
    h1 { margin: 0; font-weight: 800; letter-spacing: 0; }
    p { margin: .25rem 0 0; color: #667085; }
    .hero-actions { display: flex; flex-wrap: wrap; gap: .75rem; }
    .metrics { display: grid; grid-template-columns: repeat(auto-fit, minmax(180px, 1fr)); gap: 1rem; }
    .metrics mat-card-content { display: flex; gap: 1rem; align-items: center; }
    .metrics mat-icon { color: #0f766e; }
    .metrics strong { display: block; font-size: 1.6rem; }
    .metrics span { color: #667085; }
    .grid { padding-top: 1rem; display: grid; grid-template-columns: minmax(0, 1fr) minmax(320px, .75fr); gap: 1rem; }
    .work-list { display: grid; gap: .75rem; }
    .work-row { display: flex; justify-content: space-between; gap: 1rem; align-items: center; padding: .85rem 0; border-bottom: 1px solid #edf1f3; }
    mat-card { border-radius: 8px; }
    @media (max-width: 900px) { .hero, .grid { grid-template-columns: 1fr; display: grid; } }
  `],
})
export class ReceptionistDashboardComponent implements OnInit {
  readonly patients = signal<PatientResponse[]>([]);
  readonly appointments = signal<AppointmentResponse[]>([]);
  readonly query = signal('');
  readonly visiblePatients = computed(() => {
    const query = this.query().toLowerCase();
    return this.patients().filter((patient) => patient.fullName.toLowerCase().includes(query) || patient.mrn.toLowerCase().includes(query) || patient.phone.includes(query));
  });
  readonly metrics = computed<MetricCard[]>(() => [
    { label: 'Registered Patients', value: this.patients().length, icon: 'groups' },
    { label: 'Walk-ins Waiting', value: this.appointments().length, icon: 'queue' },
    { label: 'Billing Handoffs', value: 6, icon: 'receipt_long' },
    { label: 'Open Beds', value: 18, icon: 'bed' },
  ]);
  readonly actions: QuickAction[] = [
    { label: 'Register Patient', icon: 'person_add' },
    { label: 'Book OPD', icon: 'event_available' },
    { label: 'Create Bill', icon: 'payments' },
  ];
  readonly handoffs = [
    { title: 'Rahul Verma', subtitle: 'Consultation completed, invoice pending', status: 'ISSUED' },
    { title: 'Neha Singh', subtitle: 'Lab package estimate requested', status: 'DRAFT' },
    { title: 'Amit Kumar', subtitle: 'IPD deposit due before admission', status: 'PARTIALLY_PAID' },
  ];
  readonly patientColumns: TableColumn<PatientResponse>[] = [
    { key: 'mrn', label: 'MRN' },
    { key: 'fullName', label: 'Patient' },
    { key: 'gender', label: 'Gender' },
    { key: 'phone', label: 'Phone' },
  ];

  constructor(private readonly api: HmsApiService, private readonly snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.api.patients().subscribe({ next: (patients) => this.patients.set(patients), error: () => this.notify('Patient API unavailable') });
    this.api.appointments().subscribe({ next: (appointments) => this.appointments.set(appointments), error: () => this.notify('Queue API unavailable') });
  }

  notify(action: string): void {
    this.snackBar.open(`${action} workflow ready for form expansion`, 'Close', { duration: 2500 });
  }
}
