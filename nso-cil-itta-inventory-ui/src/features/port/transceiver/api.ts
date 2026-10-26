import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { TRANSCEIVER_PATH } from './types';
import type { Transceiver, TransceiverFilter, TransceiverInput, TransceiverSummary, TransceiverUpdateInput } from './types';
import { toTransceiverQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchTransceivers(
  filter: TransceiverFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<Transceiver>> {
  return http.get<Page<Transceiver>>(TRANSCEIVER_PATH, { ...toTransceiverQuery(filter), page, size, sort });
}

export function getTransceiver(id: string): Promise<Transceiver> {
  return http.get<Transceiver>(`${TRANSCEIVER_PATH}/${id}`);
}

export function getTransceiverByCode(code: string): Promise<Transceiver> {
  return http.get<Transceiver>(`${TRANSCEIVER_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listTransceiverSummaries(status?: LifecycleStatus): Promise<TransceiverSummary[]> {
  return http.get<TransceiverSummary[]>(`${TRANSCEIVER_PATH}/summaries`, { status });
}

export function createTransceiver(input: TransceiverInput): Promise<Transceiver> {
  return http.post<Transceiver>(TRANSCEIVER_PATH, input);
}

export function updateTransceiver(id: string, input: TransceiverUpdateInput): Promise<Transceiver> {
  return http.put<Transceiver>(`${TRANSCEIVER_PATH}/${id}`, input);
}

export function changeTransceiverStatus(id: string, status: LifecycleStatus): Promise<Transceiver> {
  return http.patch<Transceiver>(`${TRANSCEIVER_PATH}/${id}/status`, undefined, { status });
}

export function deleteTransceiver(id: string): Promise<void> {
  return http.delete(`${TRANSCEIVER_PATH}/${id}`);
}

export function bulkCreateTransceivers(inputs: TransceiverInput[]): Promise<BulkResult<Transceiver>> {
  return http.post<BulkResult<Transceiver>>(`${TRANSCEIVER_PATH}/bulk`, inputs);
}

export function getTransceiverStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${TRANSCEIVER_PATH}/stats/status`);
}

export function exportTransceiversCsv(filter: TransceiverFilter): Promise<Blob> {
  return http.blob(`${TRANSCEIVER_PATH}/export`, toTransceiverQuery(filter));
}

export function listTransceiverByPortId(portId: string, page = 0, size = 20): Promise<Page<Transceiver>> {
  return http.get<Page<Transceiver>>(`${TRANSCEIVER_PATH}/by-port-id/${portId}`, { page, size });
}
