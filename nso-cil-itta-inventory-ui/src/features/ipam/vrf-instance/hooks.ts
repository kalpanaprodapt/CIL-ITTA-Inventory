import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateVrfInstances,
  changeVrfInstanceStatus,
  createVrfInstance,
  deleteVrfInstance,
  exportVrfInstancesCsv,
  getVrfInstance,
  getVrfInstanceStatusStats,
  listVrfInstanceSummaries,
  searchVrfInstances,
  updateVrfInstance,
  listVrfInstanceByDeviceId,
} from './api';
import type { VrfInstanceFilter, VrfInstanceInput, VrfInstanceUpdateInput } from './types';

export const vrfInstanceKeys = {
  all: ['vrfInstance'] as const,
  lists: () => [...vrfInstanceKeys.all, 'list'] as const,
  list: (filter: VrfInstanceFilter, page: number, size: number, sort?: string) =>
    [...vrfInstanceKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...vrfInstanceKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...vrfInstanceKeys.all, 'summaries', status] as const,
  stats: () => [...vrfInstanceKeys.all, 'stats'] as const,
};

export function useVrfInstanceList(filter: VrfInstanceFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: vrfInstanceKeys.list(filter, page, size, sort),
    queryFn: () => searchVrfInstances(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useVrfInstance(id: string | undefined) {
  return useQuery({
    queryKey: vrfInstanceKeys.detail(id ?? ''),
    queryFn: () => getVrfInstance(id as string),
    enabled: Boolean(id),
  });
}

export function useVrfInstanceSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: vrfInstanceKeys.summaries(status),
    queryFn: () => listVrfInstanceSummaries(status),
    staleTime: 60_000,
  });
}

export function useVrfInstanceStatusStats() {
  return useQuery({
    queryKey: vrfInstanceKeys.stats(),
    queryFn: getVrfInstanceStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateVrfInstance() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: VrfInstanceInput) => createVrfInstance(input),
    onSuccess: () => client.invalidateQueries({ queryKey: vrfInstanceKeys.all }),
  });
}

export function useUpdateVrfInstance(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: VrfInstanceUpdateInput) => updateVrfInstance(id, input),
    onSuccess: (updated) => {
      client.setQueryData(vrfInstanceKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: vrfInstanceKeys.lists() });
    },
  });
}

export function useChangeVrfInstanceStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeVrfInstanceStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: vrfInstanceKeys.all }),
  });
}

export function useDeleteVrfInstance() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteVrfInstance(id),
    onSuccess: () => client.invalidateQueries({ queryKey: vrfInstanceKeys.all }),
  });
}

export function useBulkCreateVrfInstances() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: VrfInstanceInput[]) => bulkCreateVrfInstances(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: vrfInstanceKeys.all }),
  });
}

export function useExportVrfInstances() {
  return useMutation({
    mutationFn: (filter: VrfInstanceFilter) => exportVrfInstancesCsv(filter),
  });
}

export function useVrfInstancesByDeviceId(deviceId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...vrfInstanceKeys.all, 'by-device_id', deviceId, page, size],
    queryFn: () => listVrfInstanceByDeviceId(deviceId as string, page, size),
    enabled: Boolean(deviceId),
  });
}
