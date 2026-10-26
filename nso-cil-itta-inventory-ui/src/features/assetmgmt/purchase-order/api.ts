import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { PURCHASEORDER_PATH } from './types';
import type { PurchaseOrder, PurchaseOrderFilter, PurchaseOrderInput, PurchaseOrderSummary, PurchaseOrderUpdateInput } from './types';
import { toPurchaseOrderQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchPurchaseOrders(
  filter: PurchaseOrderFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<PurchaseOrder>> {
  return http.get<Page<PurchaseOrder>>(PURCHASEORDER_PATH, { ...toPurchaseOrderQuery(filter), page, size, sort });
}

export function getPurchaseOrder(id: string): Promise<PurchaseOrder> {
  return http.get<PurchaseOrder>(`${PURCHASEORDER_PATH}/${id}`);
}

export function getPurchaseOrderByCode(code: string): Promise<PurchaseOrder> {
  return http.get<PurchaseOrder>(`${PURCHASEORDER_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listPurchaseOrderSummaries(status?: LifecycleStatus): Promise<PurchaseOrderSummary[]> {
  return http.get<PurchaseOrderSummary[]>(`${PURCHASEORDER_PATH}/summaries`, { status });
}

export function createPurchaseOrder(input: PurchaseOrderInput): Promise<PurchaseOrder> {
  return http.post<PurchaseOrder>(PURCHASEORDER_PATH, input);
}

export function updatePurchaseOrder(id: string, input: PurchaseOrderUpdateInput): Promise<PurchaseOrder> {
  return http.put<PurchaseOrder>(`${PURCHASEORDER_PATH}/${id}`, input);
}

export function changePurchaseOrderStatus(id: string, status: LifecycleStatus): Promise<PurchaseOrder> {
  return http.patch<PurchaseOrder>(`${PURCHASEORDER_PATH}/${id}/status`, undefined, { status });
}

export function deletePurchaseOrder(id: string): Promise<void> {
  return http.delete(`${PURCHASEORDER_PATH}/${id}`);
}

export function bulkCreatePurchaseOrders(inputs: PurchaseOrderInput[]): Promise<BulkResult<PurchaseOrder>> {
  return http.post<BulkResult<PurchaseOrder>>(`${PURCHASEORDER_PATH}/bulk`, inputs);
}

export function getPurchaseOrderStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${PURCHASEORDER_PATH}/stats/status`);
}

export function exportPurchaseOrdersCsv(filter: PurchaseOrderFilter): Promise<Blob> {
  return http.blob(`${PURCHASEORDER_PATH}/export`, toPurchaseOrderQuery(filter));
}

export function listPurchaseOrderByVendorId(vendorId: string, page = 0, size = 20): Promise<Page<PurchaseOrder>> {
  return http.get<Page<PurchaseOrder>>(`${PURCHASEORDER_PATH}/by-vendor-id/${vendorId}`, { page, size });
}
