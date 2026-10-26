import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateVendors,
  changeVendorStatus,
  createVendor,
  deleteVendor,
  exportVendorsCsv,
  getVendor,
  getVendorStatusStats,
  listVendorSummaries,
  searchVendors,
  updateVendor,

} from './api';
import type { VendorFilter, VendorInput, VendorUpdateInput } from './types';

export const vendorKeys = {
  all: ['vendor'] as const,
  lists: () => [...vendorKeys.all, 'list'] as const,
  list: (filter: VendorFilter, page: number, size: number, sort?: string) =>
    [...vendorKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...vendorKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...vendorKeys.all, 'summaries', status] as const,
  stats: () => [...vendorKeys.all, 'stats'] as const,
};

export function useVendorList(filter: VendorFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: vendorKeys.list(filter, page, size, sort),
    queryFn: () => searchVendors(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useVendor(id: string | undefined) {
  return useQuery({
    queryKey: vendorKeys.detail(id ?? ''),
    queryFn: () => getVendor(id as string),
    enabled: Boolean(id),
  });
}

export function useVendorSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: vendorKeys.summaries(status),
    queryFn: () => listVendorSummaries(status),
    staleTime: 60_000,
  });
}

export function useVendorStatusStats() {
  return useQuery({
    queryKey: vendorKeys.stats(),
    queryFn: getVendorStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateVendor() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: VendorInput) => createVendor(input),
    onSuccess: () => client.invalidateQueries({ queryKey: vendorKeys.all }),
  });
}

export function useUpdateVendor(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: VendorUpdateInput) => updateVendor(id, input),
    onSuccess: (updated) => {
      client.setQueryData(vendorKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: vendorKeys.lists() });
    },
  });
}

export function useChangeVendorStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeVendorStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: vendorKeys.all }),
  });
}

export function useDeleteVendor() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteVendor(id),
    onSuccess: () => client.invalidateQueries({ queryKey: vendorKeys.all }),
  });
}

export function useBulkCreateVendors() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: VendorInput[]) => bulkCreateVendors(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: vendorKeys.all }),
  });
}

export function useExportVendors() {
  return useMutation({
    mutationFn: (filter: VendorFilter) => exportVendorsCsv(filter),
  });
}
