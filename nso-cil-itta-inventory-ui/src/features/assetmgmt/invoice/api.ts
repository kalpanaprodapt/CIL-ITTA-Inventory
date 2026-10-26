import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { INVOICE_PATH } from './types';
import type { Invoice, InvoiceFilter, InvoiceInput, InvoiceSummary, InvoiceUpdateInput } from './types';
import { toInvoiceQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchInvoices(
  filter: InvoiceFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<Invoice>> {
  return http.get<Page<Invoice>>(INVOICE_PATH, { ...toInvoiceQuery(filter), page, size, sort });
}

export function getInvoice(id: string): Promise<Invoice> {
  return http.get<Invoice>(`${INVOICE_PATH}/${id}`);
}

export function getInvoiceByCode(code: string): Promise<Invoice> {
  return http.get<Invoice>(`${INVOICE_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listInvoiceSummaries(status?: LifecycleStatus): Promise<InvoiceSummary[]> {
  return http.get<InvoiceSummary[]>(`${INVOICE_PATH}/summaries`, { status });
}

export function createInvoice(input: InvoiceInput): Promise<Invoice> {
  return http.post<Invoice>(INVOICE_PATH, input);
}

export function updateInvoice(id: string, input: InvoiceUpdateInput): Promise<Invoice> {
  return http.put<Invoice>(`${INVOICE_PATH}/${id}`, input);
}

export function changeInvoiceStatus(id: string, status: LifecycleStatus): Promise<Invoice> {
  return http.patch<Invoice>(`${INVOICE_PATH}/${id}/status`, undefined, { status });
}

export function deleteInvoice(id: string): Promise<void> {
  return http.delete(`${INVOICE_PATH}/${id}`);
}

export function bulkCreateInvoices(inputs: InvoiceInput[]): Promise<BulkResult<Invoice>> {
  return http.post<BulkResult<Invoice>>(`${INVOICE_PATH}/bulk`, inputs);
}

export function getInvoiceStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${INVOICE_PATH}/stats/status`);
}

export function exportInvoicesCsv(filter: InvoiceFilter): Promise<Blob> {
  return http.blob(`${INVOICE_PATH}/export`, toInvoiceQuery(filter));
}

export function listInvoiceByPoId(poId: string, page = 0, size = 20): Promise<Page<Invoice>> {
  return http.get<Page<Invoice>>(`${INVOICE_PATH}/by-po-id/${poId}`, { page, size });
}
