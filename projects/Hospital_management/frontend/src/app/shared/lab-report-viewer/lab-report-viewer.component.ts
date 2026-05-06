import { Component, Input } from '@angular/core';
import { MatExpansionModule } from '@angular/material/expansion';

@Component({
  selector: 'app-lab-report-viewer',
  standalone: true,
  imports: [MatExpansionModule],
  template: `
    <mat-accordion>
      @for (report of reports; track report.title) {
        <mat-expansion-panel>
          <mat-expansion-panel-header>{{ report.title }}</mat-expansion-panel-header>
          <p>{{ report.summary }}</p>
        </mat-expansion-panel>
      }
    </mat-accordion>
  `,
})
export class LabReportViewerComponent {
  @Input() reports: { title: string; summary: string }[] = [];
}
