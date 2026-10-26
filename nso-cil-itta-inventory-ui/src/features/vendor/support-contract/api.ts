import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { SUPPORTCONTRACT_PATH } from './types';
import type { SupportContract, SupportContractFilter, SupportContractInput, SupportContractSummary, SupportContractUpdateInput } from './types';
import { toSupportContractQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchSupportContracts(
  filter: SupportContractFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<SupportContract>> {
  return http.get<Page<SupportContract>>(SUPPORTCONTRACT_PATH, { ...toSupportContractQuery(filter), page, size, sort });
}

export function getSupportContract(id: string): Promise<SupportContract> {
  return http.get<SupportContract>(`${SUPPORTCONTRACT_PATH}/${id}`);
}

export function getSupportContractByCode(code: string): Promise<SupportContract> {
  return http.get<SupportContract>(`${SUPPORTCONTRACT_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listSupportContractSummaries(status?: LifecycleStatus): Promise<SupportContractSummary[]> {
  return http.get<SupportContractSummary[]>(`${SUPPORTCONTRACT_PATH}/summaries`, { status });
}

export function createSupportContract(input: SupportContractInput): Promise<SupportContract> {
  return http.post<SupportContract>(SUPPORTCONTRACT_PATH, input);
}

export function updateSupportContract(id: string, input: SupportContractUpdateInput): Promise<SupportContract> {
  return http.put<SupportContract>(`${SUPPORTCONTRACT_PATH}/${id}`, input);
}

export function changeSupportContractStatus(id: string, status: LifecycleStatus): Promise<SupportContract> {
  return http.patch<SupportContract>(`${SUPPORTCONTRACT_PATH}/${id}/status`, undefined, { status });
}

export function deleteSupportContract(id: string): Promise<void> {
  return http.delete(`${SUPPORTCONTRACT_PATH}/${id}`);
}

export function bulkCreateSupportContracts(inputs: SupportContractInput[]): Promise<BulkResult<SupportContract>> {
  return http.post<BulkResult<SupportContract>>(`${SUPPORTCONTRACT_PATH}/bulk`, inputs);
}

export function getSupportContractStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${SUPPORTCONTRACT_PATH}/stats/status`);
}

export function exportSupportContractsCsv(filter: SupportContractFilter): Promise<Blob> {
  return http.blob(`${SUPPORTCONTRACT_PATH}/export`, toSupportContractQuery(filter));
}

export function listSupportContractByVendorId(vendorId: string, page = 0, size = 20): Promise<Page<SupportContract>> {
  return http.get<Page<SupportContract>>(`${SUPPORTCONTRACT_PATH}/by-vendor-id/${vendorId}`, { page, size });
}
