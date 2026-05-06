import { CurrencyPipe } from '@angular/common';
import { Component, OnInit, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTabsModule } from '@angular/material/tabs';
import { ReportSummaryResponse } from '../../core/models/hms.models';
import { HmsApiService } from '../../core/services/hms-api.service';
import { DataTableWrapperComponent, TableColumn } from '../../shared/data-table-wrapper/data-table-wrapper.component';
import { StatusChipComponent } from '../../shared/status-chip/status-chip.component';
import { DashboardShellComponent } from '../dashboard/dashboard-shell.component';

interface UserRow {
  name: string;
  role: string;
  department: string;
  status: string;
}

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CurrencyPipe, MatButtonModule, MatCardModule, MatIconModule, MatSnackBarModule, MatTabsModule, DataTableWrapperComponent, StatusChipComponent, DashboardShellComponent],
  template: `
    <app-dashboard-shell title="Administration">
      <section class="metrics">
        <mat-card appearance="outlined"><mat-card-content><mat-icon>groups</mat-icon><strong>{{ summary()?.totalPatients ?? 0 }}</strong><span>Patients</span></mat-card-content></mat-card>
        <mat-card appearance="outlined"><mat-card-content><mat-icon>medical_services</mat-icon><strong>{{ summary()?.totalDoctors ?? 0 }}</strong><span>Doctors</span></mat-card-content></mat-card>
        <mat-card appearance="outlined"><mat-card-content><mat-icon>payments</mat-icon><strong>{{ summary()?.totalRevenue ?? 0 | currency:'INR' }}</strong><span>Revenue</span></mat-card-content></mat-card>
        <mat-card appearance="outlined"><mat-card-content><mat-icon>account_balance_wallet</mat-icon><strong>{{ summary()?.outstandingDues ?? 0 | currency:'INR' }}</strong><span>Dues</span></mat-card-content></mat-card>
      </section>

      <mat-tab-group>
        <mat-tab label="Command Center">
          <div class="grid">
            <mat-card appearance="outlined">
              <mat-card-header><mat-card-title>Department Analytics</mat-card-title></mat-card-header>
              <mat-card-content class="list">
                @for (row of analytics; track row.name) {
                  <div class="row-item"><div><strong>{{ row.name }}</strong><p>{{ row.detail }}</p></div><app-status-chip [label]="row.status" /></div>
                }
              </mat-card-content>
            </mat-card>
            <mat-card appearance="outlined">
              <mat-card-header><mat-card-title>Administrative Actions</mat-card-title></mat-card-header>
              <mat-card-content class="actions">
                @for (action of actions; track action) {
                  <button mat-stroked-button type="button" (click)="notify(action)"><mat-icon>settings</mat-icon>{{ action }}</button>
                }
              </mat-card-content>
            </mat-card>
          </div>
        </mat-tab>
        <mat-tab label="Users">
          <mat-card appearance="outlined">
            <mat-card-header><mat-card-title>User Management</mat-card-title></mat-card-header>
            <mat-card-content><app-data-table-wrapper [rows]="users" [columns]="userColumns" /></mat-card-content>
          </mat-card>
        </mat-tab>
        <mat-tab label="Audit">
          <mat-card appearance="outlined">
            <mat-card-header><mat-card-title>Audit Trail</mat-card-title></mat-card-header>
            <mat-card-content class="list">
              @for (event of auditEvents; track event.time) {
                <div class="row-item"><div><strong>{{ event.action }}</strong><p>{{ event.time }} | {{ event.detail }}</p></div><app-status-chip label="COMPLETED" /></div>
              }
            </mat-card-content>
          </mat-card>
        </mat-tab>
      </mat-tab-group>
    </app-dashboard-shell>
  `,
  styles: [`
    .metrics { display: grid; grid-template-columns: repeat(auto-fit, minmax(190px, 1fr)); gap: 1rem; }
    .metrics mat-card-content { display: grid; gap: .35rem; }
    mat-icon { color: #0f766e; }
    strong { font-size: 1.45rem; }
    span, p { color: #667085; }
    .grid { padding-top: 1rem; display: grid; grid-template-columns: 1.2fr .8fr; gap: 1rem; }
    .list, .actions { display: grid; gap: .75rem; }
    .row-item { display: flex; justify-content: space-between; gap: 1rem; border-bottom: 1px solid #edf1f3; padding: .75rem 0; }
    .actions { grid-template-columns: repeat(auto-fit, minmax(190px, 1fr)); }
    mat-card { border-radius: 8px; }
    @media (max-width: 900px) { .grid { grid-template-columns: 1fr; } }
  `],
})
export class AdminDashboardComponent implements OnInit {
  readonly summary = signal<ReportSummaryResponse | null>(null);
  readonly actions = ['Create user', 'Add department', 'Review audit logs', 'Stock alerts', 'Revenue report', 'Doctor workload'];
  readonly analytics = [
    { name: 'General Medicine', detail: '38 OPD visits today | 6 admissions', status: 'COMPLETED' },
    { name: 'Laboratory', detail: '42 orders | 9 reports pending', status: 'IN_PROGRESS' },
    { name: 'Pharmacy', detail: '58 dispensed | 2 low-stock alerts', status: 'LOW_STOCK' },
  ];
  readonly users: UserRow[] = [
    { name: 'System Admin', role: 'ROLE_ADMIN', department: 'Administration', status: 'Enabled' },
    { name: 'Dr. Meera Sharma', role: 'ROLE_DOCTOR', department: 'General Medicine', status: 'Enabled' },
    { name: 'Anika Pharmacist', role: 'ROLE_PHARMACIST', department: 'Pharmacy', status: 'Enabled' },
  ];
  readonly userColumns: TableColumn<UserRow>[] = [
    { key: 'name', label: 'Name' },
    { key: 'role', label: 'Role' },
    { key: 'department', label: 'Department' },
    { key: 'status', label: 'Status' },
  ];
  readonly auditEvents = [
    { time: '09:10', action: 'PATIENT_CREATED', detail: 'Reception created MRN-2026-0001' },
    { time: '10:25', action: 'PRESCRIPTION_ISSUED', detail: 'Doctor issued OPD prescription' },
    { time: '11:45', action: 'PAYMENT_RECORDED', detail: 'Reception posted invoice payment' },
  ];

  constructor(private readonly api: HmsApiService, private readonly snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.api.reportSummary().subscribe({ next: (summary) => this.summary.set(summary), error: () => this.notify('Reports API unavailable') });
  }

  notify(action: string): void {
    this.snackBar.open(`${action} workflow ready`, 'Close', { duration: 2500 });
  }
}
