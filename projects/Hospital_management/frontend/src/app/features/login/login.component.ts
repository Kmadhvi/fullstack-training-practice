import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, MatButtonModule, MatCardModule, MatFormFieldModule, MatIconModule, MatInputModule, MatProgressBarModule, MatSnackBarModule],
  template: `
    <main class="login-shell">
      <section class="login-hero">
        <div>
          <p class="eyebrow">Hospital Management System</p>
          <h1>Clinical operations, calmly organized.</h1>
          <p class="lead">Role-based workflows for reception, OPD, IPD, lab, pharmacy, billing, and administration.</p>
        </div>
      </section>

      <mat-card class="login-card" appearance="outlined">
        @if (loading()) {
          <mat-progress-bar mode="indeterminate" />
        }
        <mat-card-header>
          <mat-card-title>Sign in</mat-card-title>
          <mat-card-subtitle>Use seeded accounts like admin&#64;hms.com / password</mat-card-subtitle>
        </mat-card-header>
        <mat-card-content>
          <form [formGroup]="form" (ngSubmit)="submit()" class="d-grid gap-3 pt-3">
            <mat-form-field appearance="outline">
              <mat-label>Email</mat-label>
              <mat-icon matPrefix>alternate_email</mat-icon>
              <input matInput formControlName="email" autocomplete="email" />
            </mat-form-field>
            <mat-form-field appearance="outline">
              <mat-label>Password</mat-label>
              <mat-icon matPrefix>lock</mat-icon>
              <input matInput type="password" formControlName="password" autocomplete="current-password" />
            </mat-form-field>
            <button mat-flat-button color="primary" type="submit" [disabled]="form.invalid || loading()">Login</button>
          </form>
        </mat-card-content>
      </mat-card>
    </main>
  `,
  styles: [`
    .login-shell { min-height: 100vh; display: grid; grid-template-columns: minmax(0, 1fr) 420px; background: #eef5f3; }
    .login-hero { display: flex; align-items: end; padding: 4rem; background: linear-gradient(135deg, rgba(12, 74, 110, .86), rgba(20, 83, 45, .72)), url('https://images.unsplash.com/photo-1586773860418-d37222d8fce3?auto=format&fit=crop&w=1600&q=80') center/cover; color: white; }
    .login-hero h1 { max-width: 760px; font-size: clamp(2.6rem, 5vw, 5.4rem); line-height: .98; font-weight: 800; letter-spacing: 0; }
    .lead { max-width: 620px; font-size: 1.15rem; }
    .eyebrow { text-transform: uppercase; letter-spacing: .12rem; font-weight: 700; }
    .login-card { align-self: center; margin: 2rem; border-radius: 8px; overflow: hidden; }
    @media (max-width: 900px) { .login-shell { grid-template-columns: 1fr; } .login-hero { min-height: 42vh; padding: 2rem; } .login-card { margin: 1rem; } }
  `],
})
export class LoginComponent {
  private readonly fb = inject(FormBuilder);
  private readonly auth = inject(AuthService);
  private readonly router = inject(Router);
  private readonly snackBar = inject(MatSnackBar);

  readonly loading = signal(false);
  readonly form = this.fb.group({
    email: this.fb.control('admin@hms.com', { nonNullable: true, validators: [Validators.required, Validators.email] }),
    password: this.fb.control('password', { nonNullable: true, validators: [Validators.required] }),
  });

  submit(): void {
    if (this.form.invalid) {
      return;
    }
    this.loading.set(true);
    this.auth.login(this.form.getRawValue()).subscribe({
      next: (auth) => void this.router.navigateByUrl(this.auth.dashboardPath(auth.role)),
      error: () => {
        this.loading.set(false);
        this.snackBar.open('Login failed. Check credentials and backend availability.', 'Close', { duration: 4000 });
      },
      complete: () => this.loading.set(false),
    });
  }
}
