import { Component, EventEmitter, inject, Output } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

export interface VitalsFormValue {
  temperatureCelsius: number | null;
  pulseRate: number | null;
  systolicBp: number | null;
  diastolicBp: number | null;
  oxygenSaturation: number | null;
}

@Component({
  selector: 'app-vitals-form',
  standalone: true,
  imports: [ReactiveFormsModule, MatButtonModule, MatFormFieldModule, MatInputModule],
  template: `
    <form [formGroup]="form" class="vitals-grid" (ngSubmit)="submit()">
      <mat-form-field appearance="outline"><mat-label>Temp C</mat-label><input matInput type="number" formControlName="temperatureCelsius" /></mat-form-field>
      <mat-form-field appearance="outline"><mat-label>Pulse</mat-label><input matInput type="number" formControlName="pulseRate" /></mat-form-field>
      <mat-form-field appearance="outline"><mat-label>Systolic</mat-label><input matInput type="number" formControlName="systolicBp" /></mat-form-field>
      <mat-form-field appearance="outline"><mat-label>Diastolic</mat-label><input matInput type="number" formControlName="diastolicBp" /></mat-form-field>
      <mat-form-field appearance="outline"><mat-label>SpO2</mat-label><input matInput type="number" formControlName="oxygenSaturation" /></mat-form-field>
      <button mat-flat-button color="primary" type="submit">Save</button>
    </form>
  `,
  styles: [`.vitals-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(120px, 1fr)); gap: .75rem; align-items: start; }`],
})
export class VitalsFormComponent {
  private readonly fb = inject(FormBuilder);

  @Output() readonly save = new EventEmitter<VitalsFormValue>();
  readonly form = this.fb.group({
    temperatureCelsius: this.fb.control<number | null>(null),
    pulseRate: this.fb.control<number | null>(null, [Validators.min(20), Validators.max(250)]),
    systolicBp: this.fb.control<number | null>(null),
    diastolicBp: this.fb.control<number | null>(null),
    oxygenSaturation: this.fb.control<number | null>(null),
  });
  submit(): void {
    if (this.form.valid) {
      this.save.emit(this.form.getRawValue());
    }
  }
}
