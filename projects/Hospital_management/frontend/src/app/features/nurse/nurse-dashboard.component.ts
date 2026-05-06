import { Component, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTabsModule } from '@angular/material/tabs';
import { StatusChipComponent } from '../../shared/status-chip/status-chip.component';
import { VitalsFormComponent, VitalsFormValue } from '../../shared/vitals-form/vitals-form.component';
import { DashboardShellComponent } from '../dashboard/dashboard-shell.component';

@Component({
  selector: 'app-nurse-dashboard',
  standalone: true,
  imports: [MatButtonModule, MatCardModule, MatIconModule, MatSnackBarModule, MatTabsModule, StatusChipComponent, VitalsFormComponent, DashboardShellComponent],
  template: `
    <app-dashboard-shell title="Nursing Station">
      <section class="ward-strip">
        @for (ward of wards; track ward.name) {
          <mat-card appearance="outlined"><mat-card-content><mat-icon>bed</mat-icon><strong>{{ ward.occupied }}/{{ ward.total }}</strong><span>{{ ward.name }}</span></mat-card-content></mat-card>
        }
      </section>

      <mat-tab-group>
        <mat-tab label="IPD Board">
          <div class="grid">
            <mat-card appearance="outlined">
              <mat-card-header><mat-card-title>Active Admissions</mat-card-title></mat-card-header>
              <mat-card-content class="list">
                @for (patient of ipdPatients; track patient.bed) {
                  <div class="row-item">
                    <div><strong>{{ patient.name }}</strong><p>{{ patient.ward }} | Bed {{ patient.bed }} | {{ patient.diagnosis }}</p></div>
                    <app-status-chip [label]="patient.status" />
                  </div>
                }
              </mat-card-content>
            </mat-card>
            <mat-card appearance="outlined">
              <mat-card-header><mat-card-title>Care Tasks</mat-card-title></mat-card-header>
              <mat-card-content class="task-grid">
                @for (task of tasks; track task) {
                  <button mat-stroked-button type="button" (click)="notify(task)"><mat-icon>check_circle</mat-icon>{{ task }}</button>
                }
              </mat-card-content>
            </mat-card>
          </div>
        </mat-tab>
        <mat-tab label="Vitals">
          <mat-card appearance="outlined">
            <mat-card-header><mat-card-title>Record Vitals</mat-card-title><mat-card-subtitle>Patient context will bind after selecting an IPD record</mat-card-subtitle></mat-card-header>
            <mat-card-content><app-vitals-form (save)="saveVitals($event)" /></mat-card-content>
          </mat-card>
        </mat-tab>
        <mat-tab label="Nursing Notes">
          <mat-card appearance="outlined">
            <mat-card-header><mat-card-title>Daily Notes</mat-card-title></mat-card-header>
            <mat-card-content class="list">
              @for (note of notes; track note.time) {
                <div class="row-item"><div><strong>{{ note.time }}</strong><p>{{ note.text }}</p></div><app-status-chip label="ADMITTED" /></div>
              }
            </mat-card-content>
          </mat-card>
        </mat-tab>
      </mat-tab-group>
    </app-dashboard-shell>
  `,
  styles: [`
    .ward-strip { display: grid; grid-template-columns: repeat(auto-fit, minmax(180px, 1fr)); gap: 1rem; }
    .ward-strip mat-card-content { display: grid; gap: .4rem; }
    mat-icon { color: #0f766e; }
    strong { font-size: 1.45rem; }
    span, p { color: #667085; }
    .grid { padding-top: 1rem; display: grid; grid-template-columns: 1.2fr .8fr; gap: 1rem; }
    .list, .task-grid { display: grid; gap: .75rem; }
    .row-item { display: flex; justify-content: space-between; gap: 1rem; border-bottom: 1px solid #edf1f3; padding: .75rem 0; }
    .task-grid { grid-template-columns: repeat(auto-fit, minmax(180px, 1fr)); }
    mat-card { border-radius: 8px; }
    @media (max-width: 900px) { .grid { grid-template-columns: 1fr; } }
  `],
})
export class NurseDashboardComponent {
  readonly lastVitals = signal<VitalsFormValue | null>(null);
  readonly wards = [
    { name: 'General Ward', occupied: 28, total: 40 },
    { name: 'ICU', occupied: 8, total: 12 },
    { name: 'Post-op', occupied: 14, total: 18 },
    { name: 'Pediatrics', occupied: 11, total: 20 },
  ];
  readonly ipdPatients = [
    { name: 'Rahul Verma', ward: 'General Ward', bed: 'G-12', diagnosis: 'Asthma observation', status: 'ADMITTED' },
    { name: 'Kavya Iyer', ward: 'ICU', bed: 'I-04', diagnosis: 'Post-op monitoring', status: 'ADMITTED' },
    { name: 'Arman Khan', ward: 'Post-op', bed: 'P-09', diagnosis: 'Appendectomy recovery', status: 'ADMITTED' },
  ];
  readonly tasks = ['Medication round', 'Vitals due', 'Discharge prep', 'Doctor round list', 'Sample pickup'];
  readonly notes = [
    { time: '08:30', text: 'Vitals stable, patient comfortable after nebulization.' },
    { time: '11:00', text: 'Medication administered as prescribed, no adverse reaction.' },
    { time: '14:15', text: 'Doctor review completed, continue observation.' },
  ];

  constructor(private readonly snackBar: MatSnackBar) {}

  saveVitals(value: VitalsFormValue): void {
    this.lastVitals.set(value);
    this.notify('Vitals saved locally');
  }

  notify(message: string): void {
    this.snackBar.open(message, 'Close', { duration: 2500 });
  }
}
