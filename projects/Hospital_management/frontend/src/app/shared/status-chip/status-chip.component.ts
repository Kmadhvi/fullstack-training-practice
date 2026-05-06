import { Component, Input } from '@angular/core';
import { MatChipsModule } from '@angular/material/chips';

@Component({
  selector: 'app-status-chip',
  standalone: true,
  imports: [MatChipsModule],
  template: `<mat-chip [class]="tone">{{ label }}</mat-chip>`,
  styles: [`
    mat-chip { font-weight: 600; }
    .good { --mdc-chip-label-text-color: #0f5132; background: #d1e7dd !important; }
    .warn { --mdc-chip-label-text-color: #664d03; background: #fff3cd !important; }
    .bad { --mdc-chip-label-text-color: #842029; background: #f8d7da !important; }
  `],
})
export class StatusChipComponent {
  @Input({ required: true }) label = '';

  get tone(): 'good' | 'warn' | 'bad' {
    if (['PAID', 'COMPLETED', 'DISPENSED', 'ADMITTED'].includes(this.label)) {
      return 'good';
    }
    if (['CANCELLED', 'NO_SHOW', 'LOW_STOCK'].includes(this.label)) {
      return 'bad';
    }
    return 'warn';
  }
}
