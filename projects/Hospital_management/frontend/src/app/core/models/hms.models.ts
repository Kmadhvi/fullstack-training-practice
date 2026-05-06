export type UserRole =
  | 'ROLE_ADMIN'
  | 'ROLE_DOCTOR'
  | 'ROLE_NURSE'
  | 'ROLE_RECEPTIONIST'
  | 'ROLE_LAB_TECH'
  | 'ROLE_PHARMACIST'
  | 'ROLE_PATIENT';

export interface ApiResponse<T> {
  success: boolean;
  data: T;
  message: string;
  timestamp: string;
}

export interface AuthResponse {
  token: string;
  tokenType: string;
  userId: number;
  fullName: string;
  email: string;
  role: UserRole;
  departmentId: number | null;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface PatientResponse {
  id: number;
  mrn: string;
  fullName: string;
  gender: string;
  age: number;
  phone: string;
  bloodGroup: string;
  allergies: string | null;
}

export interface AppointmentResponse {
  id: number;
  patientName: string;
  doctorName: string;
  departmentName: string;
  status: string;
  appointmentAt: string;
  queueNumber: number | null;
  walkIn: boolean;
}

export interface ReportSummaryResponse {
  totalPatients: number;
  totalDoctors: number;
  todayAppointments: number;
  activeAdmissions: number;
  totalRevenue: number;
  outstandingDues: number;
  appointmentsByStatus: Record<string, number>;
  patientsByGender: Record<string, number>;
}

export interface BillResponse {
  id: number;
  patientName: string;
  billNumber: string;
  status: string;
  totalAmount: number;
  paidAmount: number;
  outstandingAmount: number;
}
