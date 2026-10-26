import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateWdmSystems,
  changeWdmSystemStatus,
  createWdmSystem,
  deleteWdmSystem,
  exportWdmSystemsCsv,
  getWdmSystem,
  getWdmSystemStatusStats,
  listWdmSystemSummaries,
  searchWdmSystems,
  updateWdmSystem,
  listWdmSystemByVendorId,
} from './api';
import type { WdmSystemFilter, WdmSystemInput, WdmSystemUpdateInput } from './types';

export const wdmSystemKeys = {
  all: ['wdmSystem'] as const,
  lists: () => [...wdmSystemKeys.all, 'list'] as const,
  list: (filter: WdmSystemFilter, page: number, size: number, sort?: string) =>
    [...wdmSystemKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...wdmSystemKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...wdmSystemKeys.all, 'summaries', status] as const,
  stats: () => [...wdmSystemKeys.all, 'stats'] as const,
};

export function useWdmSystemList(filter: WdmSystemFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: wdmSystemKeys.list(filter, page, size, sort),
    queryFn: () => searchWdmSystems(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useWdmSystem(id: string | undefined) {
  return useQuery({
    queryKey: wdmSystemKeys.detail(id ?? ''),
    queryFn: () => getWdmSystem(id as string),
    enabled: Boolean(id),
  });
}

export function useWdmSystemSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: wdmSystemKeys.summaries(status),
    queryFn: () => listWdmSystemSummaries(status),
    staleTime: 60_000,
  });
}

export function useWdmSystemStatusStats() {
  return useQuery({
    queryKey: wdmSystemKeys.stats(),
    queryFn: getWdmSystemStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateWdmSystem() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: WdmSystemInput) => createWdmSystem(input),
    onSuccess: () => client.invalidateQueries({ queryKey: wdmSystemKeys.all }),
  });
}

export function useUpdateWdmSystem(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: WdmSystemUpdateInput) => updateWdmSystem(id, input),
    onSuccess: (updated) => {
      client.setQueryData(wdmSystemKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: wdmSystemKeys.lists() });
    },
  });
}

export function useChangeWdmSystemStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeWdmSystemStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: wdmSystemKeys.all }),
  });
}

export function useDeleteWdmSystem() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteWdmSystem(id),
    onSuccess: () => client.invalidateQueries({ queryKey: wdmSystemKeys.all }),
  });
}

export function useBulkCreateWdmSystems() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: WdmSystemInput[]) => bulkCreateWdmSystems(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: wdmSystemKeys.all }),
  });
}

export function useExportWdmSystems() {
  return useMutation({
    mutationFn: (filter: WdmSystemFilter) => exportWdmSystemsCsv(filter),
  });
}

export function useWdmSystemsByVendorId(vendorId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...wdmSystemKeys.all, 'by-vendor_id', vendorId, page, size],
    queryFn: () => listWdmSystemByVendorId(vendorId as string, page, size),
    enabled: Boolean(vendorId),
  });
}
