import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { ApiResponse, AppointmentResponse, BillResponse, PatientResponse, ReportSummaryResponse } from '../models/hms.models';

@Injectable({ providedIn: 'root' })
export class HmsApiService {
  private readonly apiUrl = 'http://127.0.0.1:8080/api';

  constructor(private readonly http: HttpClient) {}

  patients(): Observable<PatientResponse[]> {
    return this.unwrap(this.http.get<ApiResponse<PatientResponse[]>>(`${this.apiUrl}/patients`));
  }

  appointments(): Observable<AppointmentResponse[]> {
    return this.unwrap(this.http.get<ApiResponse<AppointmentResponse[]>>(`${this.apiUrl}/appointments/walk-in-queue`));
  }

  bills(patientId: number): Observable<BillResponse[]> {
    return this.unwrap(this.http.get<ApiResponse<BillResponse[]>>(`${this.apiUrl}/billing/patient/${patientId}`));
  }

  reportSummary(): Observable<ReportSummaryResponse> {
    return this.unwrap(this.http.get<ApiResponse<ReportSummaryResponse>>(`${this.apiUrl}/reports/summary`));
  }

  lowStock(): Observable<unknown[]> {
    return this.unwrap(this.http.get<ApiResponse<unknown[]>>(`${this.apiUrl}/pharmacy/inventory/low-stock`));
  }

  private unwrap<T>(request: Observable<ApiResponse<T>>): Observable<T> {
    return request.pipe(map((response) => response.data));
  }
}
