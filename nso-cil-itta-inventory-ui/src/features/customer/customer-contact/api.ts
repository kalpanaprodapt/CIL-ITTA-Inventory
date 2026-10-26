import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { CUSTOMERCONTACT_PATH } from './types';
import type { CustomerContact, CustomerContactFilter, CustomerContactInput, CustomerContactSummary, CustomerContactUpdateInput } from './types';
import { toCustomerContactQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchCustomerContacts(
  filter: CustomerContactFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<CustomerContact>> {
  return http.get<Page<CustomerContact>>(CUSTOMERCONTACT_PATH, { ...toCustomerContactQuery(filter), page, size, sort });
}

export function getCustomerContact(id: string): Promise<CustomerContact> {
  return http.get<CustomerContact>(`${CUSTOMERCONTACT_PATH}/${id}`);
}

export function getCustomerContactByCode(code: string): Promise<CustomerContact> {
  return http.get<CustomerContact>(`${CUSTOMERCONTACT_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listCustomerContactSummaries(status?: LifecycleStatus): Promise<CustomerContactSummary[]> {
  return http.get<CustomerContactSummary[]>(`${CUSTOMERCONTACT_PATH}/summaries`, { status });
}

export function createCustomerContact(input: CustomerContactInput): Promise<CustomerContact> {
  return http.post<CustomerContact>(CUSTOMERCONTACT_PATH, input);
}

export function updateCustomerContact(id: string, input: CustomerContactUpdateInput): Promise<CustomerContact> {
  return http.put<CustomerContact>(`${CUSTOMERCONTACT_PATH}/${id}`, input);
}

export function changeCustomerContactStatus(id: string, status: LifecycleStatus): Promise<CustomerContact> {
  return http.patch<CustomerContact>(`${CUSTOMERCONTACT_PATH}/${id}/status`, undefined, { status });
}

export function deleteCustomerContact(id: string): Promise<void> {
  return http.delete(`${CUSTOMERCONTACT_PATH}/${id}`);
}

export function bulkCreateCustomerContacts(inputs: CustomerContactInput[]): Promise<BulkResult<CustomerContact>> {
  return http.post<BulkResult<CustomerContact>>(`${CUSTOMERCONTACT_PATH}/bulk`, inputs);
}

export function getCustomerContactStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${CUSTOMERCONTACT_PATH}/stats/status`);
}

export function exportCustomerContactsCsv(filter: CustomerContactFilter): Promise<Blob> {
  return http.blob(`${CUSTOMERCONTACT_PATH}/export`, toCustomerContactQuery(filter));
}

export function listCustomerContactByCustomerId(customerId: string, page = 0, size = 20): Promise<Page<CustomerContact>> {
  return http.get<Page<CustomerContact>>(`${CUSTOMERCONTACT_PATH}/by-customer-id/${customerId}`, { page, size });
}
