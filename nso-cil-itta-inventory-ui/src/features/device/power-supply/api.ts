import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { POWERSUPPLY_PATH } from './types';
import type { PowerSupply, PowerSupplyFilter, PowerSupplyInput, PowerSupplySummary, PowerSupplyUpdateInput } from './types';
import { toPowerSupplyQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchPowerSupplys(
  filter: PowerSupplyFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<PowerSupply>> {
  return http.get<Page<PowerSupply>>(POWERSUPPLY_PATH, { ...toPowerSupplyQuery(filter), page, size, sort });
}

export function getPowerSupply(id: string): Promise<PowerSupply> {
  return http.get<PowerSupply>(`${POWERSUPPLY_PATH}/${id}`);
}

export function getPowerSupplyByCode(code: string): Promise<PowerSupply> {
  return http.get<PowerSupply>(`${POWERSUPPLY_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listPowerSupplySummaries(status?: LifecycleStatus): Promise<PowerSupplySummary[]> {
  return http.get<PowerSupplySummary[]>(`${POWERSUPPLY_PATH}/summaries`, { status });
}

export function createPowerSupply(input: PowerSupplyInput): Promise<PowerSupply> {
  return http.post<PowerSupply>(POWERSUPPLY_PATH, input);
}

export function updatePowerSupply(id: string, input: PowerSupplyUpdateInput): Promise<PowerSupply> {
  return http.put<PowerSupply>(`${POWERSUPPLY_PATH}/${id}`, input);
}

export function changePowerSupplyStatus(id: string, status: LifecycleStatus): Promise<PowerSupply> {
  return http.patch<PowerSupply>(`${POWERSUPPLY_PATH}/${id}/status`, undefined, { status });
}

export function deletePowerSupply(id: string): Promise<void> {
  return http.delete(`${POWERSUPPLY_PATH}/${id}`);
}

export function bulkCreatePowerSupplys(inputs: PowerSupplyInput[]): Promise<BulkResult<PowerSupply>> {
  return http.post<BulkResult<PowerSupply>>(`${POWERSUPPLY_PATH}/bulk`, inputs);
}

export function getPowerSupplyStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${POWERSUPPLY_PATH}/stats/status`);
}

export function exportPowerSupplysCsv(filter: PowerSupplyFilter): Promise<Blob> {
  return http.blob(`${POWERSUPPLY_PATH}/export`, toPowerSupplyQuery(filter));
}

export function listPowerSupplyByChassisId(chassisId: string, page = 0, size = 20): Promise<Page<PowerSupply>> {
  return http.get<Page<PowerSupply>>(`${POWERSUPPLY_PATH}/by-chassis-id/${chassisId}`, { page, size });
}

export function listPowerSupplyByFeedId(feedId: string, page = 0, size = 20): Promise<Page<PowerSupply>> {
  return http.get<Page<PowerSupply>>(`${POWERSUPPLY_PATH}/by-feed-id/${feedId}`, { page, size });
}
