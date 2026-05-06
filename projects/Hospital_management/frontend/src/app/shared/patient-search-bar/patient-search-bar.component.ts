import { Component, EventEmitter, Output } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-patient-search-bar',
  standalone: true,
  imports: [ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatIconModule],
  template: `
    <mat-form-field appearance="outline" class="w-100">
      <mat-label>Search patients</mat-label>
      <mat-icon matPrefix>search</mat-icon>
      <input matInput [formControl]="query" (keyup.enter)="search.emit(query.value.trim())" />
    </mat-form-field>
  `,
})
export class PatientSearchBarComponent {
  @Output() readonly search = new EventEmitter<string>();
  readonly query = new FormControl('', { nonNullable: true });
}
