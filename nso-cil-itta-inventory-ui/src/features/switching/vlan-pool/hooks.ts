import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateVlanPools,
  changeVlanPoolStatus,
  createVlanPool,
  deleteVlanPool,
  exportVlanPoolsCsv,
  getVlanPool,
  getVlanPoolStatusStats,
  listVlanPoolSummaries,
  searchVlanPools,
  updateVlanPool,

} from './api';
import type { VlanPoolFilter, VlanPoolInput, VlanPoolUpdateInput } from './types';

export const vlanPoolKeys = {
  all: ['vlanPool'] as const,
  lists: () => [...vlanPoolKeys.all, 'list'] as const,
  list: (filter: VlanPoolFilter, page: number, size: number, sort?: string) =>
    [...vlanPoolKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...vlanPoolKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...vlanPoolKeys.all, 'summaries', status] as const,
  stats: () => [...vlanPoolKeys.all, 'stats'] as const,
};

export function useVlanPoolList(filter: VlanPoolFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: vlanPoolKeys.list(filter, page, size, sort),
    queryFn: () => searchVlanPools(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useVlanPool(id: string | undefined) {
  return useQuery({
    queryKey: vlanPoolKeys.detail(id ?? ''),
    queryFn: () => getVlanPool(id as string),
    enabled: Boolean(id),
  });
}

export function useVlanPoolSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: vlanPoolKeys.summaries(status),
    queryFn: () => listVlanPoolSummaries(status),
    staleTime: 60_000,
  });
}

export function useVlanPoolStatusStats() {
  return useQuery({
    queryKey: vlanPoolKeys.stats(),
    queryFn: getVlanPoolStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateVlanPool() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: VlanPoolInput) => createVlanPool(input),
    onSuccess: () => client.invalidateQueries({ queryKey: vlanPoolKeys.all }),
  });
}

export function useUpdateVlanPool(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: VlanPoolUpdateInput) => updateVlanPool(id, input),
    onSuccess: (updated) => {
      client.setQueryData(vlanPoolKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: vlanPoolKeys.lists() });
    },
  });
}

export function useChangeVlanPoolStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeVlanPoolStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: vlanPoolKeys.all }),
  });
}

export function useDeleteVlanPool() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteVlanPool(id),
    onSuccess: () => client.invalidateQueries({ queryKey: vlanPoolKeys.all }),
  });
}

export function useBulkCreateVlanPools() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: VlanPoolInput[]) => bulkCreateVlanPools(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: vlanPoolKeys.all }),
  });
}

export function useExportVlanPools() {
  return useMutation({
    mutationFn: (filter: VlanPoolFilter) => exportVlanPoolsCsv(filter),
  });
}
