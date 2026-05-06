import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { roleGuard } from './core/guards/role.guard';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'login' },
  { path: 'login', loadComponent: () => import('./features/login/login.component').then((m) => m.LoginComponent) },
  {
    path: 'receptionist',
    canActivate: [authGuard, roleGuard],
    data: { roles: ['ROLE_RECEPTIONIST', 'ROLE_ADMIN'], title: 'Reception Desk' },
    loadComponent: () => import('./features/receptionist/receptionist-dashboard.component').then((m) => m.ReceptionistDashboardComponent),
  },
  {
    path: 'doctor',
    canActivate: [authGuard, roleGuard],
    data: { roles: ['ROLE_DOCTOR', 'ROLE_ADMIN'], title: 'Doctor Workspace' },
    loadComponent: () => import('./features/doctor/doctor-dashboard.component').then((m) => m.DoctorDashboardComponent),
  },
  {
    path: 'nurse',
    canActivate: [authGuard, roleGuard],
    data: { roles: ['ROLE_NURSE', 'ROLE_ADMIN'], title: 'Nursing Station' },
    loadComponent: () => import('./features/nurse/nurse-dashboard.component').then((m) => m.NurseDashboardComponent),
  },
  {
    path: 'lab',
    canActivate: [authGuard, roleGuard],
    data: { roles: ['ROLE_LAB_TECH', 'ROLE_ADMIN'], title: 'Laboratory' },
    loadComponent: () => import('./features/lab/lab-dashboard.component').then((m) => m.LabDashboardComponent),
  },
  {
    path: 'pharmacist',
    canActivate: [authGuard, roleGuard],
    data: { roles: ['ROLE_PHARMACIST', 'ROLE_ADMIN'], title: 'Pharmacy' },
    loadComponent: () => import('./features/pharmacist/pharmacist-dashboard.component').then((m) => m.PharmacistDashboardComponent),
  },
  {
    path: 'admin',
    canActivate: [authGuard, roleGuard],
    data: { roles: ['ROLE_ADMIN'], title: 'Administration' },
    loadComponent: () => import('./features/admin/admin-dashboard.component').then((m) => m.AdminDashboardComponent),
  },
  {
    path: 'patient-portal',
    canActivate: [authGuard, roleGuard],
    data: { roles: ['ROLE_PATIENT', 'ROLE_ADMIN'], title: 'Patient Portal' },
    loadComponent: () => import('./features/patient-portal/patient-portal.component').then((m) => m.PatientPortalComponent),
  },
  { path: '**', redirectTo: 'login' },
];
