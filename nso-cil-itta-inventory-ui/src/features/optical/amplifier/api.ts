import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { AMPLIFIER_PATH } from './types';
import type { Amplifier, AmplifierFilter, AmplifierInput, AmplifierSummary, AmplifierUpdateInput } from './types';
import { toAmplifierQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchAmplifiers(
  filter: AmplifierFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<Amplifier>> {
  return http.get<Page<Amplifier>>(AMPLIFIER_PATH, { ...toAmplifierQuery(filter), page, size, sort });
}

export function getAmplifier(id: string): Promise<Amplifier> {
  return http.get<Amplifier>(`${AMPLIFIER_PATH}/${id}`);
}

export function getAmplifierByCode(code: string): Promise<Amplifier> {
  return http.get<Amplifier>(`${AMPLIFIER_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listAmplifierSummaries(status?: LifecycleStatus): Promise<AmplifierSummary[]> {
  return http.get<AmplifierSummary[]>(`${AMPLIFIER_PATH}/summaries`, { status });
}

export function createAmplifier(input: AmplifierInput): Promise<Amplifier> {
  return http.post<Amplifier>(AMPLIFIER_PATH, input);
}

export function updateAmplifier(id: string, input: AmplifierUpdateInput): Promise<Amplifier> {
  return http.put<Amplifier>(`${AMPLIFIER_PATH}/${id}`, input);
}

export function changeAmplifierStatus(id: string, status: LifecycleStatus): Promise<Amplifier> {
  return http.patch<Amplifier>(`${AMPLIFIER_PATH}/${id}/status`, undefined, { status });
}

export function deleteAmplifier(id: string): Promise<void> {
  return http.delete(`${AMPLIFIER_PATH}/${id}`);
}

export function bulkCreateAmplifiers(inputs: AmplifierInput[]): Promise<BulkResult<Amplifier>> {
  return http.post<BulkResult<Amplifier>>(`${AMPLIFIER_PATH}/bulk`, inputs);
}

export function getAmplifierStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${AMPLIFIER_PATH}/stats/status`);
}

export function exportAmplifiersCsv(filter: AmplifierFilter): Promise<Blob> {
  return http.blob(`${AMPLIFIER_PATH}/export`, toAmplifierQuery(filter));
}

export function listAmplifierBySystemId(systemId: string, page = 0, size = 20): Promise<Page<Amplifier>> {
  return http.get<Page<Amplifier>>(`${AMPLIFIER_PATH}/by-system-id/${systemId}`, { page, size });
}
