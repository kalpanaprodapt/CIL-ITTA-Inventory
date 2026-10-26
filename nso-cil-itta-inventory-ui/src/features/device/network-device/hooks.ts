import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateNetworkDevices,
  changeNetworkDeviceStatus,
  createNetworkDevice,
  deleteNetworkDevice,
  exportNetworkDevicesCsv,
  getNetworkDevice,
  getNetworkDeviceStatusStats,
  listNetworkDeviceSummaries,
  searchNetworkDevices,
  updateNetworkDevice,
  listNetworkDeviceByModelId,
  listNetworkDeviceByRackUnitId,
} from './api';
import type { NetworkDeviceFilter, NetworkDeviceInput, NetworkDeviceUpdateInput } from './types';

export const networkDeviceKeys = {
  all: ['networkDevice'] as const,
  lists: () => [...networkDeviceKeys.all, 'list'] as const,
  list: (filter: NetworkDeviceFilter, page: number, size: number, sort?: string) =>
    [...networkDeviceKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...networkDeviceKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...networkDeviceKeys.all, 'summaries', status] as const,
  stats: () => [...networkDeviceKeys.all, 'stats'] as const,
};

export function useNetworkDeviceList(filter: NetworkDeviceFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: networkDeviceKeys.list(filter, page, size, sort),
    queryFn: () => searchNetworkDevices(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useNetworkDevice(id: string | undefined) {
  return useQuery({
    queryKey: networkDeviceKeys.detail(id ?? ''),
    queryFn: () => getNetworkDevice(id as string),
    enabled: Boolean(id),
  });
}

export function useNetworkDeviceSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: networkDeviceKeys.summaries(status),
    queryFn: () => listNetworkDeviceSummaries(status),
    staleTime: 60_000,
  });
}

export function useNetworkDeviceStatusStats() {
  return useQuery({
    queryKey: networkDeviceKeys.stats(),
    queryFn: getNetworkDeviceStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateNetworkDevice() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: NetworkDeviceInput) => createNetworkDevice(input),
    onSuccess: () => client.invalidateQueries({ queryKey: networkDeviceKeys.all }),
  });
}

export function useUpdateNetworkDevice(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: NetworkDeviceUpdateInput) => updateNetworkDevice(id, input),
    onSuccess: (updated) => {
      client.setQueryData(networkDeviceKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: networkDeviceKeys.lists() });
    },
  });
}

export function useChangeNetworkDeviceStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeNetworkDeviceStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: networkDeviceKeys.all }),
  });
}

export function useDeleteNetworkDevice() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteNetworkDevice(id),
    onSuccess: () => client.invalidateQueries({ queryKey: networkDeviceKeys.all }),
  });
}

export function useBulkCreateNetworkDevices() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: NetworkDeviceInput[]) => bulkCreateNetworkDevices(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: networkDeviceKeys.all }),
  });
}

export function useExportNetworkDevices() {
  return useMutation({
    mutationFn: (filter: NetworkDeviceFilter) => exportNetworkDevicesCsv(filter),
  });
}

export function useNetworkDevicesByModelId(modelId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...networkDeviceKeys.all, 'by-model_id', modelId, page, size],
    queryFn: () => listNetworkDeviceByModelId(modelId as string, page, size),
    enabled: Boolean(modelId),
  });
}

export function useNetworkDevicesByRackUnitId(rackUnitId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...networkDeviceKeys.all, 'by-rack_unit_id', rackUnitId, page, size],
    queryFn: () => listNetworkDeviceByRackUnitId(rackUnitId as string, page, size),
    enabled: Boolean(rackUnitId),
  });
}
