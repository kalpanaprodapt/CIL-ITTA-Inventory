import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { CLOUDACCOUNT_PATH } from './types';
import type { CloudAccount, CloudAccountFilter, CloudAccountInput, CloudAccountSummary, CloudAccountUpdateInput } from './types';
import { toCloudAccountQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchCloudAccounts(
  filter: CloudAccountFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<CloudAccount>> {
  return http.get<Page<CloudAccount>>(CLOUDACCOUNT_PATH, { ...toCloudAccountQuery(filter), page, size, sort });
}

export function getCloudAccount(id: string): Promise<CloudAccount> {
  return http.get<CloudAccount>(`${CLOUDACCOUNT_PATH}/${id}`);
}

export function getCloudAccountByCode(code: string): Promise<CloudAccount> {
  return http.get<CloudAccount>(`${CLOUDACCOUNT_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listCloudAccountSummaries(status?: LifecycleStatus): Promise<CloudAccountSummary[]> {
  return http.get<CloudAccountSummary[]>(`${CLOUDACCOUNT_PATH}/summaries`, { status });
}

export function createCloudAccount(input: CloudAccountInput): Promise<CloudAccount> {
  return http.post<CloudAccount>(CLOUDACCOUNT_PATH, input);
}

export function updateCloudAccount(id: string, input: CloudAccountUpdateInput): Promise<CloudAccount> {
  return http.put<CloudAccount>(`${CLOUDACCOUNT_PATH}/${id}`, input);
}

export function changeCloudAccountStatus(id: string, status: LifecycleStatus): Promise<CloudAccount> {
  return http.patch<CloudAccount>(`${CLOUDACCOUNT_PATH}/${id}/status`, undefined, { status });
}

export function deleteCloudAccount(id: string): Promise<void> {
  return http.delete(`${CLOUDACCOUNT_PATH}/${id}`);
}

export function bulkCreateCloudAccounts(inputs: CloudAccountInput[]): Promise<BulkResult<CloudAccount>> {
  return http.post<BulkResult<CloudAccount>>(`${CLOUDACCOUNT_PATH}/bulk`, inputs);
}

export function getCloudAccountStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${CLOUDACCOUNT_PATH}/stats/status`);
}

export function exportCloudAccountsCsv(filter: CloudAccountFilter): Promise<Blob> {
  return http.blob(`${CLOUDACCOUNT_PATH}/export`, toCloudAccountQuery(filter));
}
