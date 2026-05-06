import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTabsModule } from '@angular/material/tabs';
import { LabReportViewerComponent } from '../../shared/lab-report-viewer/lab-report-viewer.component';
import { StatusChipComponent } from '../../shared/status-chip/status-chip.component';
import { DashboardShellComponent } from '../dashboard/dashboard-shell.component';

@Component({
  selector: 'app-lab-dashboard',
  standalone: true,
  imports: [MatButtonModule, MatCardModule, MatIconModule, MatSnackBarModule, MatTabsModule, LabReportViewerComponent, StatusChipComponent, DashboardShellComponent],
  template: `
    <app-dashboard-shell title="Laboratory">
      <section class="metrics">
        @for (metric of metrics; track metric.label) {
          <mat-card appearance="outlined"><mat-card-content><mat-icon>{{ metric.icon }}</mat-icon><strong>{{ metric.value }}</strong><span>{{ metric.label }}</span></mat-card-content></mat-card>
        }
      </section>

      <mat-tab-group>
        <mat-tab label="Orders">
          <div class="grid">
            <mat-card appearance="outlined">
              <mat-card-header><mat-card-title>Sample Collection Queue</mat-card-title></mat-card-header>
              <mat-card-content class="list">
                @for (order of orders; track order.code) {
                  <div class="row-item">
                    <div><strong>{{ order.code }} | {{ order.patient }}</strong><p>{{ order.tests }}</p></div>
                    <app-status-chip [label]="order.status" />
                  </div>
                }
              </mat-card-content>
            </mat-card>
            <mat-card appearance="outlined">
              <mat-card-header><mat-card-title>Workstation Actions</mat-card-title></mat-card-header>
              <mat-card-content class="actions">
                @for (action of actions; track action) {
                  <button mat-stroked-button type="button" (click)="notify(action)"><mat-icon>science</mat-icon>{{ action }}</button>
                }
              </mat-card-content>
            </mat-card>
          </div>
        </mat-tab>
        <mat-tab label="Results">
          <mat-card appearance="outlined">
            <mat-card-header><mat-card-title>Report Preview</mat-card-title></mat-card-header>
            <mat-card-content><app-lab-report-viewer [reports]="reports" /></mat-card-content>
          </mat-card>
        </mat-tab>
      </mat-tab-group>
    </app-dashboard-shell>
  `,
  styles: [`
    .metrics { display: grid; grid-template-columns: repeat(auto-fit, minmax(180px, 1fr)); gap: 1rem; }
    .metrics mat-card-content { display: grid; gap: .35rem; }
    mat-icon { color: #0f766e; }
    strong { font-size: 1.55rem; }
    span, p { color: #667085; }
    .grid { padding-top: 1rem; display: grid; grid-template-columns: 1.3fr .7fr; gap: 1rem; }
    .list, .actions { display: grid; gap: .75rem; }
    .row-item { display: flex; justify-content: space-between; gap: 1rem; border-bottom: 1px solid #edf1f3; padding: .75rem 0; }
    mat-card { border-radius: 8px; }
    @media (max-width: 900px) { .grid { grid-template-columns: 1fr; } }
  `],
})
export class LabDashboardComponent {
  readonly metrics = [
    { label: 'Orders Today', value: 42, icon: 'assignment' },
    { label: 'Samples Pending', value: 13, icon: 'hourglass_top' },
    { label: 'Results to Verify', value: 9, icon: 'fact_check' },
    { label: 'Critical Alerts', value: 2, icon: 'priority_high' },
  ];
  readonly orders = [
    { code: 'LAB-1024', patient: 'Rahul Verma', tests: 'CBC, ESR', status: 'SAMPLE_COLLECTED' },
    { code: 'LAB-1025', patient: 'Kavya Iyer', tests: 'LFT, KFT', status: 'IN_PROGRESS' },
    { code: 'LAB-1026', patient: 'Arman Khan', tests: 'Blood culture', status: 'ORDERED' },
  ];
  readonly actions = ['Collect sample', 'Enter result', 'Verify report', 'Print barcode', 'Mark critical'];
  readonly reports = [
    { title: 'CBC - LAB-1024', summary: 'Differential count is within reference range.' },
    { title: 'KFT - LAB-1025', summary: 'Creatinine normal. Electrolytes pending verification.' },
  ];

  constructor(private readonly snackBar: MatSnackBar) {}

  notify(action: string): void {
    this.snackBar.open(`${action} workflow ready`, 'Close', { duration: 2500 });
  }
}
