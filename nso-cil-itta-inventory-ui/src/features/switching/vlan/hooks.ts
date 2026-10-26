import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateVlans,
  changeVlanStatus,
  createVlan,
  deleteVlan,
  exportVlansCsv,
  getVlan,
  getVlanStatusStats,
  listVlanSummaries,
  searchVlans,
  updateVlan,
  listVlanByPoolId,
} from './api';
import type { VlanFilter, VlanInput, VlanUpdateInput } from './types';

export const vlanKeys = {
  all: ['vlan'] as const,
  lists: () => [...vlanKeys.all, 'list'] as const,
  list: (filter: VlanFilter, page: number, size: number, sort?: string) =>
    [...vlanKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...vlanKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...vlanKeys.all, 'summaries', status] as const,
  stats: () => [...vlanKeys.all, 'stats'] as const,
};

export function useVlanList(filter: VlanFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: vlanKeys.list(filter, page, size, sort),
    queryFn: () => searchVlans(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useVlan(id: string | undefined) {
  return useQuery({
    queryKey: vlanKeys.detail(id ?? ''),
    queryFn: () => getVlan(id as string),
    enabled: Boolean(id),
  });
}

export function useVlanSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: vlanKeys.summaries(status),
    queryFn: () => listVlanSummaries(status),
    staleTime: 60_000,
  });
}

export function useVlanStatusStats() {
  return useQuery({
    queryKey: vlanKeys.stats(),
    queryFn: getVlanStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateVlan() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: VlanInput) => createVlan(input),
    onSuccess: () => client.invalidateQueries({ queryKey: vlanKeys.all }),
  });
}

export function useUpdateVlan(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: VlanUpdateInput) => updateVlan(id, input),
    onSuccess: (updated) => {
      client.setQueryData(vlanKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: vlanKeys.lists() });
    },
  });
}

export function useChangeVlanStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeVlanStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: vlanKeys.all }),
  });
}

export function useDeleteVlan() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteVlan(id),
    onSuccess: () => client.invalidateQueries({ queryKey: vlanKeys.all }),
  });
}

export function useBulkCreateVlans() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: VlanInput[]) => bulkCreateVlans(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: vlanKeys.all }),
  });
}

export function useExportVlans() {
  return useMutation({
    mutationFn: (filter: VlanFilter) => exportVlansCsv(filter),
  });
}

export function useVlansByPoolId(poolId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...vlanKeys.all, 'by-pool_id', poolId, page, size],
    queryFn: () => listVlanByPoolId(poolId as string, page, size),
    enabled: Boolean(poolId),
  });
}
