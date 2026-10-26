import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { RADIOUNIT_PATH } from './types';
import type { RadioUnit, RadioUnitFilter, RadioUnitInput, RadioUnitSummary, RadioUnitUpdateInput } from './types';
import { toRadioUnitQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchRadioUnits(
  filter: RadioUnitFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<RadioUnit>> {
  return http.get<Page<RadioUnit>>(RADIOUNIT_PATH, { ...toRadioUnitQuery(filter), page, size, sort });
}

export function getRadioUnit(id: string): Promise<RadioUnit> {
  return http.get<RadioUnit>(`${RADIOUNIT_PATH}/${id}`);
}

export function getRadioUnitByCode(code: string): Promise<RadioUnit> {
  return http.get<RadioUnit>(`${RADIOUNIT_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listRadioUnitSummaries(status?: LifecycleStatus): Promise<RadioUnitSummary[]> {
  return http.get<RadioUnitSummary[]>(`${RADIOUNIT_PATH}/summaries`, { status });
}

export function createRadioUnit(input: RadioUnitInput): Promise<RadioUnit> {
  return http.post<RadioUnit>(RADIOUNIT_PATH, input);
}

export function updateRadioUnit(id: string, input: RadioUnitUpdateInput): Promise<RadioUnit> {
  return http.put<RadioUnit>(`${RADIOUNIT_PATH}/${id}`, input);
}

export function changeRadioUnitStatus(id: string, status: LifecycleStatus): Promise<RadioUnit> {
  return http.patch<RadioUnit>(`${RADIOUNIT_PATH}/${id}/status`, undefined, { status });
}

export function deleteRadioUnit(id: string): Promise<void> {
  return http.delete(`${RADIOUNIT_PATH}/${id}`);
}

export function bulkCreateRadioUnits(inputs: RadioUnitInput[]): Promise<BulkResult<RadioUnit>> {
  return http.post<BulkResult<RadioUnit>>(`${RADIOUNIT_PATH}/bulk`, inputs);
}

export function getRadioUnitStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${RADIOUNIT_PATH}/stats/status`);
}

export function exportRadioUnitsCsv(filter: RadioUnitFilter): Promise<Blob> {
  return http.blob(`${RADIOUNIT_PATH}/export`, toRadioUnitQuery(filter));
}

export function listRadioUnitBySiteId(siteId: string, page = 0, size = 20): Promise<Page<RadioUnit>> {
  return http.get<Page<RadioUnit>>(`${RADIOUNIT_PATH}/by-site-id/${siteId}`, { page, size });
}
