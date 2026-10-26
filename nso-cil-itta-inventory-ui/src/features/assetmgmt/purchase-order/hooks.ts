import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreatePurchaseOrders,
  changePurchaseOrderStatus,
  createPurchaseOrder,
  deletePurchaseOrder,
  exportPurchaseOrdersCsv,
  getPurchaseOrder,
  getPurchaseOrderStatusStats,
  listPurchaseOrderSummaries,
  searchPurchaseOrders,
  updatePurchaseOrder,
  listPurchaseOrderByVendorId,
} from './api';
import type { PurchaseOrderFilter, PurchaseOrderInput, PurchaseOrderUpdateInput } from './types';

export const purchaseOrderKeys = {
  all: ['purchaseOrder'] as const,
  lists: () => [...purchaseOrderKeys.all, 'list'] as const,
  list: (filter: PurchaseOrderFilter, page: number, size: number, sort?: string) =>
    [...purchaseOrderKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...purchaseOrderKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...purchaseOrderKeys.all, 'summaries', status] as const,
  stats: () => [...purchaseOrderKeys.all, 'stats'] as const,
};

export function usePurchaseOrderList(filter: PurchaseOrderFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: purchaseOrderKeys.list(filter, page, size, sort),
    queryFn: () => searchPurchaseOrders(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function usePurchaseOrder(id: string | undefined) {
  return useQuery({
    queryKey: purchaseOrderKeys.detail(id ?? ''),
    queryFn: () => getPurchaseOrder(id as string),
    enabled: Boolean(id),
  });
}

export function usePurchaseOrderSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: purchaseOrderKeys.summaries(status),
    queryFn: () => listPurchaseOrderSummaries(status),
    staleTime: 60_000,
  });
}

export function usePurchaseOrderStatusStats() {
  return useQuery({
    queryKey: purchaseOrderKeys.stats(),
    queryFn: getPurchaseOrderStatusStats,
    staleTime: 30_000,
  });
}

export function useCreatePurchaseOrder() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: PurchaseOrderInput) => createPurchaseOrder(input),
    onSuccess: () => client.invalidateQueries({ queryKey: purchaseOrderKeys.all }),
  });
}

export function useUpdatePurchaseOrder(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: PurchaseOrderUpdateInput) => updatePurchaseOrder(id, input),
    onSuccess: (updated) => {
      client.setQueryData(purchaseOrderKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: purchaseOrderKeys.lists() });
    },
  });
}

export function useChangePurchaseOrderStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changePurchaseOrderStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: purchaseOrderKeys.all }),
  });
}

export function useDeletePurchaseOrder() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deletePurchaseOrder(id),
    onSuccess: () => client.invalidateQueries({ queryKey: purchaseOrderKeys.all }),
  });
}

export function useBulkCreatePurchaseOrders() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: PurchaseOrderInput[]) => bulkCreatePurchaseOrders(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: purchaseOrderKeys.all }),
  });
}

export function useExportPurchaseOrders() {
  return useMutation({
    mutationFn: (filter: PurchaseOrderFilter) => exportPurchaseOrdersCsv(filter),
  });
}

export function usePurchaseOrdersByVendorId(vendorId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...purchaseOrderKeys.all, 'by-vendor_id', vendorId, page, size],
    queryFn: () => listPurchaseOrderByVendorId(vendorId as string, page, size),
    enabled: Boolean(vendorId),
  });
}
