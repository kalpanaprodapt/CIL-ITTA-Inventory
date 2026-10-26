import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { CONTRACT_PATH } from './types';
import type { Contract, ContractFilter, ContractInput, ContractSummary, ContractUpdateInput } from './types';
import { toContractQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchContracts(
  filter: ContractFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<Contract>> {
  return http.get<Page<Contract>>(CONTRACT_PATH, { ...toContractQuery(filter), page, size, sort });
}

export function getContract(id: string): Promise<Contract> {
  return http.get<Contract>(`${CONTRACT_PATH}/${id}`);
}

export function getContractByCode(code: string): Promise<Contract> {
  return http.get<Contract>(`${CONTRACT_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listContractSummaries(status?: LifecycleStatus): Promise<ContractSummary[]> {
  return http.get<ContractSummary[]>(`${CONTRACT_PATH}/summaries`, { status });
}

export function createContract(input: ContractInput): Promise<Contract> {
  return http.post<Contract>(CONTRACT_PATH, input);
}

export function updateContract(id: string, input: ContractUpdateInput): Promise<Contract> {
  return http.put<Contract>(`${CONTRACT_PATH}/${id}`, input);
}

export function changeContractStatus(id: string, status: LifecycleStatus): Promise<Contract> {
  return http.patch<Contract>(`${CONTRACT_PATH}/${id}/status`, undefined, { status });
}

export function deleteContract(id: string): Promise<void> {
  return http.delete(`${CONTRACT_PATH}/${id}`);
}

export function bulkCreateContracts(inputs: ContractInput[]): Promise<BulkResult<Contract>> {
  return http.post<BulkResult<Contract>>(`${CONTRACT_PATH}/bulk`, inputs);
}

export function getContractStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${CONTRACT_PATH}/stats/status`);
}

export function exportContractsCsv(filter: ContractFilter): Promise<Blob> {
  return http.blob(`${CONTRACT_PATH}/export`, toContractQuery(filter));
}

export function listContractByCustomerId(customerId: string, page = 0, size = 20): Promise<Page<Contract>> {
  return http.get<Page<Contract>>(`${CONTRACT_PATH}/by-customer-id/${customerId}`, { page, size });
}
