import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { RESERVATION_PATH } from './types';
import type { Reservation, ReservationFilter, ReservationInput, ReservationSummary, ReservationUpdateInput } from './types';
import { toReservationQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchReservations(
  filter: ReservationFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<Reservation>> {
  return http.get<Page<Reservation>>(RESERVATION_PATH, { ...toReservationQuery(filter), page, size, sort });
}

export function getReservation(id: string): Promise<Reservation> {
  return http.get<Reservation>(`${RESERVATION_PATH}/${id}`);
}

export function getReservationByCode(code: string): Promise<Reservation> {
  return http.get<Reservation>(`${RESERVATION_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listReservationSummaries(status?: LifecycleStatus): Promise<ReservationSummary[]> {
  return http.get<ReservationSummary[]>(`${RESERVATION_PATH}/summaries`, { status });
}

export function createReservation(input: ReservationInput): Promise<Reservation> {
  return http.post<Reservation>(RESERVATION_PATH, input);
}

export function updateReservation(id: string, input: ReservationUpdateInput): Promise<Reservation> {
  return http.put<Reservation>(`${RESERVATION_PATH}/${id}`, input);
}

export function changeReservationStatus(id: string, status: LifecycleStatus): Promise<Reservation> {
  return http.patch<Reservation>(`${RESERVATION_PATH}/${id}/status`, undefined, { status });
}

export function deleteReservation(id: string): Promise<void> {
  return http.delete(`${RESERVATION_PATH}/${id}`);
}

export function bulkCreateReservations(inputs: ReservationInput[]): Promise<BulkResult<Reservation>> {
  return http.post<BulkResult<Reservation>>(`${RESERVATION_PATH}/bulk`, inputs);
}

export function getReservationStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${RESERVATION_PATH}/stats/status`);
}

export function exportReservationsCsv(filter: ReservationFilter): Promise<Blob> {
  return http.blob(`${RESERVATION_PATH}/export`, toReservationQuery(filter));
}
