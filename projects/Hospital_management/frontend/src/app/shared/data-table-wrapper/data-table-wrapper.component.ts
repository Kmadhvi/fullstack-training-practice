import { Component, Input } from '@angular/core';
import { MatTableModule } from '@angular/material/table';

export interface TableColumn<T> {
  key: keyof T & string;
  label: string;
}

@Component({
  selector: 'app-data-table-wrapper',
  standalone: true,
  imports: [MatTableModule],
  template: `
    <table mat-table [dataSource]="rows" class="w-100">
      @for (column of columns; track column.label) {
        <ng-container [matColumnDef]="column.key.toString()">
          <th mat-header-cell *matHeaderCellDef>{{ column.label }}</th>
          <td mat-cell *matCellDef="let row">{{ cell(row, column.key) }}</td>
        </ng-container>
      }
      <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
      <tr mat-row *matRowDef="let row; columns: displayedColumns;"></tr>
    </table>
  `,
})
export class DataTableWrapperComponent<T extends object> {
  @Input({ required: true }) rows: T[] = [];
  @Input({ required: true }) columns: TableColumn<T>[] = [];

  get displayedColumns(): string[] {
    return this.columns.map((column) => column.key.toString());
  }

  cell(row: T, key: keyof T & string): string {
    const value = row[key];
    return value == null ? '' : String(value);
  }
}
