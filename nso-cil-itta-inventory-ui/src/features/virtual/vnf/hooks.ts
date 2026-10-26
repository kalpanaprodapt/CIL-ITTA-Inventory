import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateVnfs,
  changeVnfStatus,
  createVnf,
  deleteVnf,
  exportVnfsCsv,
  getVnf,
  getVnfStatusStats,
  listVnfSummaries,
  searchVnfs,
  updateVnf,

} from './api';
import type { VnfFilter, VnfInput, VnfUpdateInput } from './types';

export const vnfKeys = {
  all: ['vnf'] as const,
  lists: () => [...vnfKeys.all, 'list'] as const,
  list: (filter: VnfFilter, page: number, size: number, sort?: string) =>
    [...vnfKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...vnfKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...vnfKeys.all, 'summaries', status] as const,
  stats: () => [...vnfKeys.all, 'stats'] as const,
};

export function useVnfList(filter: VnfFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: vnfKeys.list(filter, page, size, sort),
    queryFn: () => searchVnfs(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useVnf(id: string | undefined) {
  return useQuery({
    queryKey: vnfKeys.detail(id ?? ''),
    queryFn: () => getVnf(id as string),
    enabled: Boolean(id),
  });
}

export function useVnfSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: vnfKeys.summaries(status),
    queryFn: () => listVnfSummaries(status),
    staleTime: 60_000,
  });
}

export function useVnfStatusStats() {
  return useQuery({
    queryKey: vnfKeys.stats(),
    queryFn: getVnfStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateVnf() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: VnfInput) => createVnf(input),
    onSuccess: () => client.invalidateQueries({ queryKey: vnfKeys.all }),
  });
}

export function useUpdateVnf(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: VnfUpdateInput) => updateVnf(id, input),
    onSuccess: (updated) => {
      client.setQueryData(vnfKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: vnfKeys.lists() });
    },
  });
}

export function useChangeVnfStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeVnfStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: vnfKeys.all }),
  });
}

export function useDeleteVnf() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteVnf(id),
    onSuccess: () => client.invalidateQueries({ queryKey: vnfKeys.all }),
  });
}

export function useBulkCreateVnfs() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: VnfInput[]) => bulkCreateVnfs(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: vnfKeys.all }),
  });
}

export function useExportVnfs() {
  return useMutation({
    mutationFn: (filter: VnfFilter) => exportVnfsCsv(filter),
  });
}
