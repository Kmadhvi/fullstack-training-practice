import { Component, Input } from '@angular/core';
import { MatListModule } from '@angular/material/list';

@Component({
  selector: 'app-prescription-viewer',
  standalone: true,
  imports: [MatListModule],
  template: `
    <mat-list>
      @for (item of medicines; track item) {
        <mat-list-item>{{ item }}</mat-list-item>
      } @empty {
        <mat-list-item>No active prescription selected.</mat-list-item>
      }
    </mat-list>
  `,
})
export class PrescriptionViewerComponent {
  @Input() medicines: string[] = [];
}
