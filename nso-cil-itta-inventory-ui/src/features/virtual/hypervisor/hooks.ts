import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateHypervisors,
  changeHypervisorStatus,
  createHypervisor,
  deleteHypervisor,
  exportHypervisorsCsv,
  getHypervisor,
  getHypervisorStatusStats,
  listHypervisorSummaries,
  searchHypervisors,
  updateHypervisor,
  listHypervisorByHostDeviceId,
} from './api';
import type { HypervisorFilter, HypervisorInput, HypervisorUpdateInput } from './types';

export const hypervisorKeys = {
  all: ['hypervisor'] as const,
  lists: () => [...hypervisorKeys.all, 'list'] as const,
  list: (filter: HypervisorFilter, page: number, size: number, sort?: string) =>
    [...hypervisorKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...hypervisorKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...hypervisorKeys.all, 'summaries', status] as const,
  stats: () => [...hypervisorKeys.all, 'stats'] as const,
};

export function useHypervisorList(filter: HypervisorFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: hypervisorKeys.list(filter, page, size, sort),
    queryFn: () => searchHypervisors(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useHypervisor(id: string | undefined) {
  return useQuery({
    queryKey: hypervisorKeys.detail(id ?? ''),
    queryFn: () => getHypervisor(id as string),
    enabled: Boolean(id),
  });
}

export function useHypervisorSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: hypervisorKeys.summaries(status),
    queryFn: () => listHypervisorSummaries(status),
    staleTime: 60_000,
  });
}

export function useHypervisorStatusStats() {
  return useQuery({
    queryKey: hypervisorKeys.stats(),
    queryFn: getHypervisorStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateHypervisor() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: HypervisorInput) => createHypervisor(input),
    onSuccess: () => client.invalidateQueries({ queryKey: hypervisorKeys.all }),
  });
}

export function useUpdateHypervisor(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: HypervisorUpdateInput) => updateHypervisor(id, input),
    onSuccess: (updated) => {
      client.setQueryData(hypervisorKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: hypervisorKeys.lists() });
    },
  });
}

export function useChangeHypervisorStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeHypervisorStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: hypervisorKeys.all }),
  });
}

export function useDeleteHypervisor() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteHypervisor(id),
    onSuccess: () => client.invalidateQueries({ queryKey: hypervisorKeys.all }),
  });
}

export function useBulkCreateHypervisors() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: HypervisorInput[]) => bulkCreateHypervisors(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: hypervisorKeys.all }),
  });
}

export function useExportHypervisors() {
  return useMutation({
    mutationFn: (filter: HypervisorFilter) => exportHypervisorsCsv(filter),
  });
}

export function useHypervisorsByHostDeviceId(hostDeviceId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...hypervisorKeys.all, 'by-host_device_id', hostDeviceId, page, size],
    queryFn: () => listHypervisorByHostDeviceId(hostDeviceId as string, page, size),
    enabled: Boolean(hostDeviceId),
  });
}
