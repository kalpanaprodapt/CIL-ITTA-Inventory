import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { CUSTOMERACCOUNT_PATH } from './types';
import type { CustomerAccount, CustomerAccountFilter, CustomerAccountInput, CustomerAccountSummary, CustomerAccountUpdateInput } from './types';
import { toCustomerAccountQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchCustomerAccounts(
  filter: CustomerAccountFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<CustomerAccount>> {
  return http.get<Page<CustomerAccount>>(CUSTOMERACCOUNT_PATH, { ...toCustomerAccountQuery(filter), page, size, sort });
}

export function getCustomerAccount(id: string): Promise<CustomerAccount> {
  return http.get<CustomerAccount>(`${CUSTOMERACCOUNT_PATH}/${id}`);
}

export function getCustomerAccountByCode(code: string): Promise<CustomerAccount> {
  return http.get<CustomerAccount>(`${CUSTOMERACCOUNT_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listCustomerAccountSummaries(status?: LifecycleStatus): Promise<CustomerAccountSummary[]> {
  return http.get<CustomerAccountSummary[]>(`${CUSTOMERACCOUNT_PATH}/summaries`, { status });
}

export function createCustomerAccount(input: CustomerAccountInput): Promise<CustomerAccount> {
  return http.post<CustomerAccount>(CUSTOMERACCOUNT_PATH, input);
}

export function updateCustomerAccount(id: string, input: CustomerAccountUpdateInput): Promise<CustomerAccount> {
  return http.put<CustomerAccount>(`${CUSTOMERACCOUNT_PATH}/${id}`, input);
}

export function changeCustomerAccountStatus(id: string, status: LifecycleStatus): Promise<CustomerAccount> {
  return http.patch<CustomerAccount>(`${CUSTOMERACCOUNT_PATH}/${id}/status`, undefined, { status });
}

export function deleteCustomerAccount(id: string): Promise<void> {
  return http.delete(`${CUSTOMERACCOUNT_PATH}/${id}`);
}

export function bulkCreateCustomerAccounts(inputs: CustomerAccountInput[]): Promise<BulkResult<CustomerAccount>> {
  return http.post<BulkResult<CustomerAccount>>(`${CUSTOMERACCOUNT_PATH}/bulk`, inputs);
}

export function getCustomerAccountStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${CUSTOMERACCOUNT_PATH}/stats/status`);
}

export function exportCustomerAccountsCsv(filter: CustomerAccountFilter): Promise<Blob> {
  return http.blob(`${CUSTOMERACCOUNT_PATH}/export`, toCustomerAccountQuery(filter));
}

export function listCustomerAccountByCustomerId(customerId: string, page = 0, size = 20): Promise<Page<CustomerAccount>> {
  return http.get<Page<CustomerAccount>>(`${CUSTOMERACCOUNT_PATH}/by-customer-id/${customerId}`, { page, size });
}
