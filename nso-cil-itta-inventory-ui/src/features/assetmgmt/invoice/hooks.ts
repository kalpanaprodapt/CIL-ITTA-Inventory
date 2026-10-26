import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateInvoices,
  changeInvoiceStatus,
  createInvoice,
  deleteInvoice,
  exportInvoicesCsv,
  getInvoice,
  getInvoiceStatusStats,
  listInvoiceSummaries,
  searchInvoices,
  updateInvoice,
  listInvoiceByPoId,
} from './api';
import type { InvoiceFilter, InvoiceInput, InvoiceUpdateInput } from './types';

export const invoiceKeys = {
  all: ['invoice'] as const,
  lists: () => [...invoiceKeys.all, 'list'] as const,
  list: (filter: InvoiceFilter, page: number, size: number, sort?: string) =>
    [...invoiceKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...invoiceKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...invoiceKeys.all, 'summaries', status] as const,
  stats: () => [...invoiceKeys.all, 'stats'] as const,
};

export function useInvoiceList(filter: InvoiceFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: invoiceKeys.list(filter, page, size, sort),
    queryFn: () => searchInvoices(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useInvoice(id: string | undefined) {
  return useQuery({
    queryKey: invoiceKeys.detail(id ?? ''),
    queryFn: () => getInvoice(id as string),
    enabled: Boolean(id),
  });
}

export function useInvoiceSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: invoiceKeys.summaries(status),
    queryFn: () => listInvoiceSummaries(status),
    staleTime: 60_000,
  });
}

export function useInvoiceStatusStats() {
  return useQuery({
    queryKey: invoiceKeys.stats(),
    queryFn: getInvoiceStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateInvoice() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: InvoiceInput) => createInvoice(input),
    onSuccess: () => client.invalidateQueries({ queryKey: invoiceKeys.all }),
  });
}

export function useUpdateInvoice(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: InvoiceUpdateInput) => updateInvoice(id, input),
    onSuccess: (updated) => {
      client.setQueryData(invoiceKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: invoiceKeys.lists() });
    },
  });
}

export function useChangeInvoiceStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeInvoiceStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: invoiceKeys.all }),
  });
}

export function useDeleteInvoice() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteInvoice(id),
    onSuccess: () => client.invalidateQueries({ queryKey: invoiceKeys.all }),
  });
}

export function useBulkCreateInvoices() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: InvoiceInput[]) => bulkCreateInvoices(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: invoiceKeys.all }),
  });
}

export function useExportInvoices() {
  return useMutation({
    mutationFn: (filter: InvoiceFilter) => exportInvoicesCsv(filter),
  });
}

export function useInvoicesByPoId(poId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...invoiceKeys.all, 'by-po_id', poId, page, size],
    queryFn: () => listInvoiceByPoId(poId as string, page, size),
    enabled: Boolean(poId),
  });
}
