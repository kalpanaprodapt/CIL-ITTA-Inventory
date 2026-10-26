import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateIpPools,
  changeIpPoolStatus,
  createIpPool,
  deleteIpPool,
  exportIpPoolsCsv,
  getIpPool,
  getIpPoolStatusStats,
  listIpPoolSummaries,
  searchIpPools,
  updateIpPool,
  listIpPoolBySubnetId,
} from './api';
import type { IpPoolFilter, IpPoolInput, IpPoolUpdateInput } from './types';

export const ipPoolKeys = {
  all: ['ipPool'] as const,
  lists: () => [...ipPoolKeys.all, 'list'] as const,
  list: (filter: IpPoolFilter, page: number, size: number, sort?: string) =>
    [...ipPoolKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...ipPoolKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...ipPoolKeys.all, 'summaries', status] as const,
  stats: () => [...ipPoolKeys.all, 'stats'] as const,
};

export function useIpPoolList(filter: IpPoolFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: ipPoolKeys.list(filter, page, size, sort),
    queryFn: () => searchIpPools(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useIpPool(id: string | undefined) {
  return useQuery({
    queryKey: ipPoolKeys.detail(id ?? ''),
    queryFn: () => getIpPool(id as string),
    enabled: Boolean(id),
  });
}

export function useIpPoolSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: ipPoolKeys.summaries(status),
    queryFn: () => listIpPoolSummaries(status),
    staleTime: 60_000,
  });
}

export function useIpPoolStatusStats() {
  return useQuery({
    queryKey: ipPoolKeys.stats(),
    queryFn: getIpPoolStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateIpPool() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: IpPoolInput) => createIpPool(input),
    onSuccess: () => client.invalidateQueries({ queryKey: ipPoolKeys.all }),
  });
}

export function useUpdateIpPool(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: IpPoolUpdateInput) => updateIpPool(id, input),
    onSuccess: (updated) => {
      client.setQueryData(ipPoolKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: ipPoolKeys.lists() });
    },
  });
}

export function useChangeIpPoolStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeIpPoolStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: ipPoolKeys.all }),
  });
}

export function useDeleteIpPool() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteIpPool(id),
    onSuccess: () => client.invalidateQueries({ queryKey: ipPoolKeys.all }),
  });
}

export function useBulkCreateIpPools() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: IpPoolInput[]) => bulkCreateIpPools(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: ipPoolKeys.all }),
  });
}

export function useExportIpPools() {
  return useMutation({
    mutationFn: (filter: IpPoolFilter) => exportIpPoolsCsv(filter),
  });
}

export function useIpPoolsBySubnetId(subnetId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...ipPoolKeys.all, 'by-subnet_id', subnetId, page, size],
    queryFn: () => listIpPoolBySubnetId(subnetId as string, page, size),
    enabled: Boolean(subnetId),
  });
}
