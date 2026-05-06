import { CurrencyPipe } from '@angular/common';
import { Component, Input } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { BillResponse } from '../../core/models/hms.models';
import { StatusChipComponent } from '../status-chip/status-chip.component';

@Component({
  selector: 'app-bill-summary-card',
  standalone: true,
  imports: [CurrencyPipe, MatCardModule, StatusChipComponent],
  template: `
    <mat-card appearance="outlined">
      <mat-card-header>
        <mat-card-title>{{ bill.billNumber }}</mat-card-title>
        <mat-card-subtitle>{{ bill.patientName }}</mat-card-subtitle>
      </mat-card-header>
      <mat-card-content class="d-flex justify-content-between align-items-end pt-3">
        <div>
          <div class="text-muted small">Outstanding</div>
          <strong>{{ bill.outstandingAmount | currency:'INR' }}</strong>
        </div>
        <app-status-chip [label]="bill.status" />
      </mat-card-content>
    </mat-card>
  `,
})
export class BillSummaryCardComponent {
  @Input({ required: true }) bill!: BillResponse;
}
