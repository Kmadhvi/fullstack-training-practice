import { CurrencyPipe, DatePipe } from '@angular/common';
import { Component, OnInit, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTabsModule } from '@angular/material/tabs';
import { AppointmentResponse, BillResponse } from '../../core/models/hms.models';
import { AuthService } from '../../core/services/auth.service';
import { HmsApiService } from '../../core/services/hms-api.service';
import { AppointmentCalendarComponent } from '../../shared/appointment-calendar/appointment-calendar.component';
import { BillSummaryCardComponent } from '../../shared/bill-summary-card/bill-summary-card.component';
import { LabReportViewerComponent } from '../../shared/lab-report-viewer/lab-report-viewer.component';
import { PrescriptionViewerComponent } from '../../shared/prescription-viewer/prescription-viewer.component';
import { StatusChipComponent } from '../../shared/status-chip/status-chip.component';
import { DashboardShellComponent } from '../dashboard/dashboard-shell.component';

@Component({
  selector: 'app-patient-portal',
  standalone: true,
  imports: [CurrencyPipe, DatePipe, MatButtonModule, MatCardModule, MatIconModule, MatSnackBarModule, MatTabsModule, AppointmentCalendarComponent, BillSummaryCardComponent, LabReportViewerComponent, PrescriptionViewerComponent, StatusChipComponent, DashboardShellComponent],
  template: `
    <app-dashboard-shell title="Patient Portal">
      <section class="portal-hero">
        <div>
          <p class="eyebrow">Welcome back</p>
          <h1>{{ auth.currentUser()?.fullName }}</h1>
          <p>Appointments, prescriptions, lab reports, bills, and discharge documents in one place.</p>
        </div>
        <button mat-flat-button color="primary" type="button" (click)="notify('Book appointment')"><mat-icon>event_available</mat-icon> Book Appointment</button>
      </section>

      <section class="metrics">
        <mat-card appearance="outlined"><mat-card-content><mat-icon>event</mat-icon><strong>{{ appointments().length }}</strong><span>Upcoming visits</span></mat-card-content></mat-card>
        <mat-card appearance="outlined"><mat-card-content><mat-icon>receipt_long</mat-icon><strong>{{ totalDue() | currency:'INR' }}</strong><span>Outstanding</span></mat-card-content></mat-card>
        <mat-card appearance="outlined"><mat-card-content><mat-icon>science</mat-icon><strong>2</strong><span>Reports</span></mat-card-content></mat-card>
        <mat-card appearance="outlined"><mat-card-content><mat-icon>medication</mat-icon><strong>3</strong><span>Active medicines</span></mat-card-content></mat-card>
      </section>

      <mat-tab-group>
        <mat-tab label="Timeline">
          <div class="grid">
            <mat-card appearance="outlined"><mat-card-header><mat-card-title>Appointments</mat-card-title></mat-card-header><mat-card-content><app-appointment-calendar [appointments]="appointments()" /></mat-card-content></mat-card>
            <mat-card appearance="outlined"><mat-card-header><mat-card-title>Care Summary</mat-card-title></mat-card-header><mat-card-content class="list">
              @for (item of timeline; track item.title) {
                <div class="row-item"><div><strong>{{ item.title }}</strong><p>{{ item.detail }}</p></div><app-status-chip [label]="item.status" /></div>
              }
            </mat-card-content></mat-card>
          </div>
        </mat-tab>
        <mat-tab label="Records">
          <div class="grid">
            <mat-card appearance="outlined"><mat-card-header><mat-card-title>Prescriptions</mat-card-title></mat-card-header><mat-card-content><app-prescription-viewer [medicines]="medicines" /></mat-card-content></mat-card>
            <mat-card appearance="outlined"><mat-card-header><mat-card-title>Lab Reports</mat-card-title></mat-card-header><mat-card-content><app-lab-report-viewer [reports]="reports" /></mat-card-content></mat-card>
          </div>
        </mat-tab>
        <mat-tab label="Bills">
          <div class="bill-grid">
            @for (bill of bills(); track bill.id) {
              <app-bill-summary-card [bill]="bill" />
            } @empty {
              <mat-card appearance="outlined"><mat-card-content>No bills found for this demo patient.</mat-card-content></mat-card>
            }
          </div>
        </mat-tab>
      </mat-tab-group>
    </app-dashboard-shell>
  `,
  styles: [`
    .portal-hero { display: flex; justify-content: space-between; gap: 1rem; align-items: end; padding: 1.25rem; background: #fff; border: 1px solid #dfe5e8; border-radius: 8px; }
    .eyebrow { color: #0f766e; text-transform: uppercase; font-weight: 800; letter-spacing: .1rem; }
    h1 { margin: 0; font-weight: 800; letter-spacing: 0; }
    p, span { color: #667085; }
    .metrics { display: grid; grid-template-columns: repeat(auto-fit, minmax(180px, 1fr)); gap: 1rem; }
    .metrics mat-card-content { display: grid; gap: .35rem; }
    mat-icon { color: #0f766e; }
    strong { font-size: 1.45rem; }
    .grid { padding-top: 1rem; display: grid; grid-template-columns: repeat(auto-fit, minmax(340px, 1fr)); gap: 1rem; }
    .bill-grid { padding-top: 1rem; display: grid; grid-template-columns: repeat(auto-fit, minmax(260px, 1fr)); gap: 1rem; }
    .list { display: grid; gap: .75rem; }
    .row-item { display: flex; justify-content: space-between; gap: 1rem; border-bottom: 1px solid #edf1f3; padding: .75rem 0; }
    mat-card { border-radius: 8px; }
    @media (max-width: 900px) { .portal-hero { display: grid; } }
  `],
})
export class PatientPortalComponent implements OnInit {
  readonly appointments = signal<AppointmentResponse[]>([]);
  readonly bills = signal<BillResponse[]>([]);
  readonly totalDue = signal(0);
  readonly medicines = ['Paracetamol 500mg | twice daily', 'Pantoprazole 40mg | before breakfast', 'Vitamin D3 | weekly'];
  readonly reports = [{ title: 'CBC', summary: 'Reviewed by doctor. No urgent abnormality.' }, { title: 'Chest X-ray', summary: 'No acute findings reported.' }];
  readonly timeline = [
    { title: 'OPD Consultation', detail: 'General Medicine review completed', status: 'COMPLETED' },
    { title: 'Lab Order', detail: 'CBC report available', status: 'COMPLETED' },
    { title: 'Pharmacy', detail: 'Prescription ready for pickup', status: 'ISSUED' },
  ];

  constructor(readonly auth: AuthService, private readonly api: HmsApiService, private readonly snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.api.appointments().subscribe({ next: (appointments) => this.appointments.set(appointments), error: () => this.notify('Appointment API unavailable') });
    this.api.bills(1).subscribe({
      next: (bills) => {
        this.bills.set(bills);
        this.totalDue.set(bills.reduce((sum, bill) => sum + bill.outstandingAmount, 0));
      },
      error: () => this.notify('Billing API unavailable'),
    });
  }

  notify(action: string): void {
    this.snackBar.open(`${action} workflow ready`, 'Close', { duration: 2500 });
  }
}
