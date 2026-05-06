import { CurrencyPipe } from '@angular/common';
import { Component, OnInit, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTabsModule } from '@angular/material/tabs';
import { HmsApiService } from '../../core/services/hms-api.service';
import { PrescriptionViewerComponent } from '../../shared/prescription-viewer/prescription-viewer.component';
import { StatusChipComponent } from '../../shared/status-chip/status-chip.component';
import { DashboardShellComponent } from '../dashboard/dashboard-shell.component';

@Component({
  selector: 'app-pharmacist-dashboard',
  standalone: true,
  imports: [CurrencyPipe, MatButtonModule, MatCardModule, MatIconModule, MatSnackBarModule, MatTabsModule, PrescriptionViewerComponent, StatusChipComponent, DashboardShellComponent],
  template: `
    <app-dashboard-shell title="Pharmacy">
      <section class="metrics">
        @for (metric of metrics(); track metric.label) {
          <mat-card appearance="outlined"><mat-card-content><mat-icon>{{ metric.icon }}</mat-icon><strong>{{ metric.value }}</strong><span>{{ metric.label }}</span></mat-card-content></mat-card>
        }
      </section>

      <mat-tab-group>
        <mat-tab label="Dispense">
          <div class="grid">
            <mat-card appearance="outlined">
              <mat-card-header><mat-card-title>Prescription Queue</mat-card-title></mat-card-header>
              <mat-card-content class="list">
                @for (rx of queue; track rx.patient) {
                  <div class="row-item"><div><strong>{{ rx.patient }}</strong><p>{{ rx.items }}</p></div><app-status-chip [label]="rx.status" /></div>
                }
              </mat-card-content>
            </mat-card>
            <mat-card appearance="outlined">
              <mat-card-header><mat-card-title>Selected Prescription</mat-card-title></mat-card-header>
              <mat-card-content><app-prescription-viewer [medicines]="selectedMedicines" /></mat-card-content>
              <mat-card-actions><button mat-flat-button color="primary" type="button" (click)="notify('Dispense')"><mat-icon>medication</mat-icon> Dispense</button></mat-card-actions>
            </mat-card>
          </div>
        </mat-tab>
        <mat-tab label="Inventory">
          <mat-card appearance="outlined">
            <mat-card-header><mat-card-title>Stock Alerts</mat-card-title></mat-card-header>
            <mat-card-content class="list">
              @for (item of stockAlerts; track item.name) {
                <div class="row-item"><div><strong>{{ item.name }}</strong><p>{{ item.qty }} units | reorder at {{ item.reorder }} | {{ item.price | currency:'INR' }}</p></div><app-status-chip label="LOW_STOCK" /></div>
              }
            </mat-card-content>
          </mat-card>
        </mat-tab>
      </mat-tab-group>
    </app-dashboard-shell>
  `,
  styles: [`
    .metrics { display: grid; grid-template-columns: repeat(auto-fit, minmax(180px, 1fr)); gap: 1rem; }
    .metrics mat-card-content { display: grid; gap: .35rem; }
    mat-icon { color: #0f766e; }
    strong { font-size: 1.5rem; }
    span, p { color: #667085; }
    .grid { padding-top: 1rem; display: grid; grid-template-columns: 1.2fr .8fr; gap: 1rem; }
    .list { display: grid; gap: .75rem; }
    .row-item { display: flex; justify-content: space-between; gap: 1rem; border-bottom: 1px solid #edf1f3; padding: .75rem 0; }
    mat-card { border-radius: 8px; }
    @media (max-width: 900px) { .grid { grid-template-columns: 1fr; } }
  `],
})
export class PharmacistDashboardComponent implements OnInit {
  readonly lowStockCount = signal(0);
  readonly metrics = signal([
    { label: 'Pending Prescriptions', value: 16, icon: 'receipt_long' },
    { label: 'Dispensed Today', value: 58, icon: 'done_all' },
    { label: 'Low Stock', value: 0, icon: 'warning' },
    { label: 'Expiring Soon', value: 4, icon: 'event_busy' },
  ]);
  readonly queue = [
    { patient: 'Rahul Verma', items: '2 medicines | OPD', status: 'ISSUED' },
    { patient: 'Kavya Iyer', items: '5 medicines | IPD', status: 'PARTIALLY_DISPENSED' },
    { patient: 'Arman Khan', items: '3 medicines | Discharge', status: 'ISSUED' },
  ];
  readonly selectedMedicines = ['Paracetamol 500mg | Qty 10', 'Pantoprazole 40mg | Qty 7'];
  readonly stockAlerts = [
    { name: 'Amoxicillin 500mg', qty: 32, reorder: 50, price: 8 },
    { name: 'Salbutamol inhaler', qty: 9, reorder: 20, price: 180 },
  ];

  constructor(private readonly api: HmsApiService, private readonly snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.api.lowStock().subscribe({
      next: (items) => {
        this.lowStockCount.set(items.length);
        this.metrics.update((metrics) => metrics.map((metric) => metric.label === 'Low Stock' ? { ...metric, value: items.length } : metric));
      },
      error: () => this.notify('Stock alert API unavailable'),
    });
  }

  notify(action: string): void {
    this.snackBar.open(`${action} workflow ready`, 'Close', { duration: 2500 });
  }
}
