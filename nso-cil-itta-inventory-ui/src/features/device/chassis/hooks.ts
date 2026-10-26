import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateChassiss,
  changeChassisStatus,
  createChassis,
  deleteChassis,
  exportChassissCsv,
  getChassis,
  getChassisStatusStats,
  listChassisSummaries,
  searchChassiss,
  updateChassis,
  listChassisByDeviceId,
} from './api';
import type { ChassisFilter, ChassisInput, ChassisUpdateInput } from './types';

export const chassisKeys = {
  all: ['chassis'] as const,
  lists: () => [...chassisKeys.all, 'list'] as const,
  list: (filter: ChassisFilter, page: number, size: number, sort?: string) =>
    [...chassisKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...chassisKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...chassisKeys.all, 'summaries', status] as const,
  stats: () => [...chassisKeys.all, 'stats'] as const,
};

export function useChassisList(filter: ChassisFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: chassisKeys.list(filter, page, size, sort),
    queryFn: () => searchChassiss(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useChassis(id: string | undefined) {
  return useQuery({
    queryKey: chassisKeys.detail(id ?? ''),
    queryFn: () => getChassis(id as string),
    enabled: Boolean(id),
  });
}

export function useChassisSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: chassisKeys.summaries(status),
    queryFn: () => listChassisSummaries(status),
    staleTime: 60_000,
  });
}

export function useChassisStatusStats() {
  return useQuery({
    queryKey: chassisKeys.stats(),
    queryFn: getChassisStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateChassis() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ChassisInput) => createChassis(input),
    onSuccess: () => client.invalidateQueries({ queryKey: chassisKeys.all }),
  });
}

export function useUpdateChassis(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ChassisUpdateInput) => updateChassis(id, input),
    onSuccess: (updated) => {
      client.setQueryData(chassisKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: chassisKeys.lists() });
    },
  });
}

export function useChangeChassisStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeChassisStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: chassisKeys.all }),
  });
}

export function useDeleteChassis() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteChassis(id),
    onSuccess: () => client.invalidateQueries({ queryKey: chassisKeys.all }),
  });
}

export function useBulkCreateChassiss() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: ChassisInput[]) => bulkCreateChassiss(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: chassisKeys.all }),
  });
}

export function useExportChassiss() {
  return useMutation({
    mutationFn: (filter: ChassisFilter) => exportChassissCsv(filter),
  });
}

export function useChassissByDeviceId(deviceId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...chassisKeys.all, 'by-device_id', deviceId, page, size],
    queryFn: () => listChassisByDeviceId(deviceId as string, page, size),
    enabled: Boolean(deviceId),
  });
}
