import { DatePipe } from '@angular/common';
import { Component, Input } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { AppointmentResponse } from '../../core/models/hms.models';
import { StatusChipComponent } from '../status-chip/status-chip.component';

@Component({
  selector: 'app-appointment-calendar',
  standalone: true,
  imports: [DatePipe, MatCardModule, StatusChipComponent],
  template: `
    <div class="calendar-list">
      @for (appointment of appointments; track appointment.id) {
        <mat-card appearance="outlined">
          <mat-card-content class="d-flex justify-content-between gap-3 align-items-center">
            <div>
              <strong>{{ appointment.patientName }}</strong>
              <div class="text-muted small">{{ appointment.doctorName }} · {{ appointment.appointmentAt | date:'short' }}</div>
            </div>
            <app-status-chip [label]="appointment.status" />
          </mat-card-content>
        </mat-card>
      } @empty {
        <p class="text-muted mb-0">No appointments in this queue.</p>
      }
    </div>
  `,
  styles: [`.calendar-list { display: grid; gap: .75rem; }`],
})
export class AppointmentCalendarComponent {
  @Input({ required: true }) appointments: AppointmentResponse[] = [];
}
