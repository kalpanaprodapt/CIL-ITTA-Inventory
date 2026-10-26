import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateLagGroups,
  changeLagGroupStatus,
  createLagGroup,
  deleteLagGroup,
  exportLagGroupsCsv,
  getLagGroup,
  getLagGroupStatusStats,
  listLagGroupSummaries,
  searchLagGroups,
  updateLagGroup,
  listLagGroupByDeviceId,
} from './api';
import type { LagGroupFilter, LagGroupInput, LagGroupUpdateInput } from './types';

export const lagGroupKeys = {
  all: ['lagGroup'] as const,
  lists: () => [...lagGroupKeys.all, 'list'] as const,
  list: (filter: LagGroupFilter, page: number, size: number, sort?: string) =>
    [...lagGroupKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...lagGroupKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...lagGroupKeys.all, 'summaries', status] as const,
  stats: () => [...lagGroupKeys.all, 'stats'] as const,
};

export function useLagGroupList(filter: LagGroupFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: lagGroupKeys.list(filter, page, size, sort),
    queryFn: () => searchLagGroups(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useLagGroup(id: string | undefined) {
  return useQuery({
    queryKey: lagGroupKeys.detail(id ?? ''),
    queryFn: () => getLagGroup(id as string),
    enabled: Boolean(id),
  });
}

export function useLagGroupSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: lagGroupKeys.summaries(status),
    queryFn: () => listLagGroupSummaries(status),
    staleTime: 60_000,
  });
}

export function useLagGroupStatusStats() {
  return useQuery({
    queryKey: lagGroupKeys.stats(),
    queryFn: getLagGroupStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateLagGroup() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: LagGroupInput) => createLagGroup(input),
    onSuccess: () => client.invalidateQueries({ queryKey: lagGroupKeys.all }),
  });
}

export function useUpdateLagGroup(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: LagGroupUpdateInput) => updateLagGroup(id, input),
    onSuccess: (updated) => {
      client.setQueryData(lagGroupKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: lagGroupKeys.lists() });
    },
  });
}

export function useChangeLagGroupStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeLagGroupStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: lagGroupKeys.all }),
  });
}

export function useDeleteLagGroup() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteLagGroup(id),
    onSuccess: () => client.invalidateQueries({ queryKey: lagGroupKeys.all }),
  });
}

export function useBulkCreateLagGroups() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: LagGroupInput[]) => bulkCreateLagGroups(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: lagGroupKeys.all }),
  });
}

export function useExportLagGroups() {
  return useMutation({
    mutationFn: (filter: LagGroupFilter) => exportLagGroupsCsv(filter),
  });
}

export function useLagGroupsByDeviceId(deviceId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...lagGroupKeys.all, 'by-device_id', deviceId, page, size],
    queryFn: () => listLagGroupByDeviceId(deviceId as string, page, size),
    enabled: Boolean(deviceId),
  });
}
