import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateSupportContracts,
  changeSupportContractStatus,
  createSupportContract,
  deleteSupportContract,
  exportSupportContractsCsv,
  getSupportContract,
  getSupportContractStatusStats,
  listSupportContractSummaries,
  searchSupportContracts,
  updateSupportContract,
  listSupportContractByVendorId,
} from './api';
import type { SupportContractFilter, SupportContractInput, SupportContractUpdateInput } from './types';

export const supportContractKeys = {
  all: ['supportContract'] as const,
  lists: () => [...supportContractKeys.all, 'list'] as const,
  list: (filter: SupportContractFilter, page: number, size: number, sort?: string) =>
    [...supportContractKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...supportContractKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...supportContractKeys.all, 'summaries', status] as const,
  stats: () => [...supportContractKeys.all, 'stats'] as const,
};

export function useSupportContractList(filter: SupportContractFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: supportContractKeys.list(filter, page, size, sort),
    queryFn: () => searchSupportContracts(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useSupportContract(id: string | undefined) {
  return useQuery({
    queryKey: supportContractKeys.detail(id ?? ''),
    queryFn: () => getSupportContract(id as string),
    enabled: Boolean(id),
  });
}

export function useSupportContractSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: supportContractKeys.summaries(status),
    queryFn: () => listSupportContractSummaries(status),
    staleTime: 60_000,
  });
}

export function useSupportContractStatusStats() {
  return useQuery({
    queryKey: supportContractKeys.stats(),
    queryFn: getSupportContractStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateSupportContract() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: SupportContractInput) => createSupportContract(input),
    onSuccess: () => client.invalidateQueries({ queryKey: supportContractKeys.all }),
  });
}

export function useUpdateSupportContract(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: SupportContractUpdateInput) => updateSupportContract(id, input),
    onSuccess: (updated) => {
      client.setQueryData(supportContractKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: supportContractKeys.lists() });
    },
  });
}

export function useChangeSupportContractStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeSupportContractStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: supportContractKeys.all }),
  });
}

export function useDeleteSupportContract() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteSupportContract(id),
    onSuccess: () => client.invalidateQueries({ queryKey: supportContractKeys.all }),
  });
}

export function useBulkCreateSupportContracts() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: SupportContractInput[]) => bulkCreateSupportContracts(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: supportContractKeys.all }),
  });
}

export function useExportSupportContracts() {
  return useMutation({
    mutationFn: (filter: SupportContractFilter) => exportSupportContractsCsv(filter),
  });
}

export function useSupportContractsByVendorId(vendorId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...supportContractKeys.all, 'by-vendor_id', vendorId, page, size],
    queryFn: () => listSupportContractByVendorId(vendorId as string, page, size),
    enabled: Boolean(vendorId),
  });
}
