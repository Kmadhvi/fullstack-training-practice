import { Component, OnInit, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTabsModule } from '@angular/material/tabs';
import { AppointmentResponse, PatientResponse } from '../../core/models/hms.models';
import { HmsApiService } from '../../core/services/hms-api.service';
import { AppointmentCalendarComponent } from '../../shared/appointment-calendar/appointment-calendar.component';
import { DataTableWrapperComponent, TableColumn } from '../../shared/data-table-wrapper/data-table-wrapper.component';
import { LabReportViewerComponent } from '../../shared/lab-report-viewer/lab-report-viewer.component';
import { PrescriptionViewerComponent } from '../../shared/prescription-viewer/prescription-viewer.component';
import { StatusChipComponent } from '../../shared/status-chip/status-chip.component';
import { DashboardShellComponent } from '../dashboard/dashboard-shell.component';

@Component({
  selector: 'app-doctor-dashboard',
  standalone: true,
  imports: [MatButtonModule, MatCardModule, MatIconModule, MatSnackBarModule, MatTabsModule, AppointmentCalendarComponent, DataTableWrapperComponent, LabReportViewerComponent, PrescriptionViewerComponent, StatusChipComponent, DashboardShellComponent],
  template: `
    <app-dashboard-shell title="Doctor Workspace">
      <section class="doctor-board">
        <mat-card appearance="outlined" class="focus">
          <mat-card-content>
            <div>
              <p class="eyebrow">Current OPD</p>
              <h1>EMR, consult notes, prescriptions, and orders</h1>
              <p>Designed for fast clinical review while keeping patient context visible.</p>
            </div>
            <button mat-flat-button color="primary" type="button" (click)="notify('Consultation note')"><mat-icon>edit_note</mat-icon> New Note</button>
          </mat-card-content>
        </mat-card>
        @for (card of cards; track card.label) {
          <mat-card appearance="outlined"><mat-card-content><mat-icon>{{ card.icon }}</mat-icon><strong>{{ card.value }}</strong><span>{{ card.label }}</span></mat-card-content></mat-card>
        }
      </section>

      <mat-tab-group>
        <mat-tab label="Schedule">
          <div class="grid">
            <mat-card appearance="outlined"><mat-card-header><mat-card-title>Checked-in Patients</mat-card-title></mat-card-header><mat-card-content><app-appointment-calendar [appointments]="appointments()" /></mat-card-content></mat-card>
            <mat-card appearance="outlined"><mat-card-header><mat-card-title>Clinical Alerts</mat-card-title></mat-card-header><mat-card-content class="list">
              @for (alert of alerts; track alert.title) {
                <div class="row-item"><div><strong>{{ alert.title }}</strong><p>{{ alert.subtitle }}</p></div><app-status-chip [label]="alert.status" /></div>
              }
            </mat-card-content></mat-card>
          </div>
        </mat-tab>
        <mat-tab label="EMR">
          <div class="grid">
            <mat-card appearance="outlined"><mat-card-header><mat-card-title>Recent Patients</mat-card-title></mat-card-header><mat-card-content><app-data-table-wrapper [rows]="patients()" [columns]="patientColumns" /></mat-card-content></mat-card>
            <mat-card appearance="outlined"><mat-card-header><mat-card-title>Prescription Draft</mat-card-title></mat-card-header><mat-card-content><app-prescription-viewer [medicines]="draftMedicines" /></mat-card-content></mat-card>
          </div>
        </mat-tab>
        <mat-tab label="Diagnostics">
          <mat-card appearance="outlined"><mat-card-header><mat-card-title>Lab Review</mat-card-title></mat-card-header><mat-card-content><app-lab-report-viewer [reports]="labReports" /></mat-card-content></mat-card>
        </mat-tab>
      </mat-tab-group>
    </app-dashboard-shell>
  `,
  styles: [`
    .doctor-board { display: grid; grid-template-columns: minmax(0, 1.6fr) repeat(3, minmax(150px, .5fr)); gap: 1rem; }
    .focus mat-card-content { min-height: 170px; display: flex; justify-content: space-between; gap: 1rem; align-items: end; }
    h1 { margin: 0; max-width: 720px; font-weight: 800; letter-spacing: 0; }
    p { margin: .25rem 0 0; color: #667085; }
    .eyebrow { color: #0f766e; text-transform: uppercase; font-weight: 800; letter-spacing: .1rem; }
    .doctor-board > mat-card:not(.focus) mat-card-content { height: 100%; display: grid; gap: .5rem; align-content: center; }
    .doctor-board mat-icon { color: #0f766e; }
    .doctor-board strong { font-size: 1.7rem; }
    .doctor-board span { color: #667085; }
    .grid { padding-top: 1rem; display: grid; grid-template-columns: repeat(auto-fit, minmax(340px, 1fr)); gap: 1rem; }
    .list { display: grid; gap: .75rem; }
    .row-item { display: flex; justify-content: space-between; gap: 1rem; border-bottom: 1px solid #edf1f3; padding: .75rem 0; }
    mat-card { border-radius: 8px; }
    @media (max-width: 1100px) { .doctor-board { grid-template-columns: 1fr 1fr; } .focus { grid-column: 1 / -1; } }
  `],
})
export class DoctorDashboardComponent implements OnInit {
  readonly patients = signal<PatientResponse[]>([]);
  readonly appointments = signal<AppointmentResponse[]>([]);
  readonly cards = [
    { label: 'Today OPD', value: 24, icon: 'event_available' },
    { label: 'Pending Notes', value: 7, icon: 'edit_note' },
    { label: 'Lab Reviews', value: 5, icon: 'biotech' },
  ];
  readonly alerts = [
    { title: 'Rahul Verma', subtitle: 'Asthma history, penicillin allergy', status: 'CHECKED_IN' },
    { title: 'Follow-up due', subtitle: '3 patients due for review this week', status: 'SCHEDULED' },
    { title: 'Lab result ready', subtitle: 'CBC and lipid profile ready for sign-off', status: 'COMPLETED' },
  ];
  readonly draftMedicines = ['Paracetamol 500mg | 1-0-1 | 5 days', 'Pantoprazole 40mg | 1-0-0 | 7 days'];
  readonly labReports = [{ title: 'CBC - Rahul Verma', summary: 'Hemoglobin and WBC within reference range.' }, { title: 'Lipid Profile', summary: 'LDL mildly elevated; counsel lifestyle changes.' }];
  readonly patientColumns: TableColumn<PatientResponse>[] = [
    { key: 'mrn', label: 'MRN' },
    { key: 'fullName', label: 'Patient' },
    { key: 'bloodGroup', label: 'Blood' },
    { key: 'allergies', label: 'Allergies' },
  ];

  constructor(private readonly api: HmsApiService, private readonly snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.api.patients().subscribe({ next: (patients) => this.patients.set(patients), error: () => this.notify('Patient API unavailable') });
    this.api.appointments().subscribe({ next: (appointments) => this.appointments.set(appointments), error: () => this.notify('Schedule API unavailable') });
  }

  notify(message: string): void {
    this.snackBar.open(`${message} workflow ready`, 'Close', { duration: 2500 });
  }
}
