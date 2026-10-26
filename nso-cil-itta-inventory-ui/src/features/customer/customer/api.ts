import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { CUSTOMER_PATH } from './types';
import type { Customer, CustomerFilter, CustomerInput, CustomerSummary, CustomerUpdateInput } from './types';
import { toCustomerQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchCustomers(
  filter: CustomerFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<Customer>> {
  return http.get<Page<Customer>>(CUSTOMER_PATH, { ...toCustomerQuery(filter), page, size, sort });
}

export function getCustomer(id: string): Promise<Customer> {
  return http.get<Customer>(`${CUSTOMER_PATH}/${id}`);
}

export function getCustomerByCode(code: string): Promise<Customer> {
  return http.get<Customer>(`${CUSTOMER_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listCustomerSummaries(status?: LifecycleStatus): Promise<CustomerSummary[]> {
  return http.get<CustomerSummary[]>(`${CUSTOMER_PATH}/summaries`, { status });
}

export function createCustomer(input: CustomerInput): Promise<Customer> {
  return http.post<Customer>(CUSTOMER_PATH, input);
}

export function updateCustomer(id: string, input: CustomerUpdateInput): Promise<Customer> {
  return http.put<Customer>(`${CUSTOMER_PATH}/${id}`, input);
}

export function changeCustomerStatus(id: string, status: LifecycleStatus): Promise<Customer> {
  return http.patch<Customer>(`${CUSTOMER_PATH}/${id}/status`, undefined, { status });
}

export function deleteCustomer(id: string): Promise<void> {
  return http.delete(`${CUSTOMER_PATH}/${id}`);
}

export function bulkCreateCustomers(inputs: CustomerInput[]): Promise<BulkResult<Customer>> {
  return http.post<BulkResult<Customer>>(`${CUSTOMER_PATH}/bulk`, inputs);
}

export function getCustomerStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${CUSTOMER_PATH}/stats/status`);
}

export function exportCustomersCsv(filter: CustomerFilter): Promise<Blob> {
  return http.blob(`${CUSTOMER_PATH}/export`, toCustomerQuery(filter));
}
