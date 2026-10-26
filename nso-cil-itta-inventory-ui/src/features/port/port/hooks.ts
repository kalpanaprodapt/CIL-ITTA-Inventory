import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreatePorts,
  changePortStatus,
  createPort,
  deletePort,
  exportPortsCsv,
  getPort,
  getPortStatusStats,
  listPortSummaries,
  searchPorts,
  updatePort,
  listPortByDeviceId,
  listPortByCardId,
} from './api';
import type { PortFilter, PortInput, PortUpdateInput } from './types';

export const portKeys = {
  all: ['port'] as const,
  lists: () => [...portKeys.all, 'list'] as const,
  list: (filter: PortFilter, page: number, size: number, sort?: string) =>
    [...portKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...portKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...portKeys.all, 'summaries', status] as const,
  stats: () => [...portKeys.all, 'stats'] as const,
};

export function usePortList(filter: PortFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: portKeys.list(filter, page, size, sort),
    queryFn: () => searchPorts(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function usePort(id: string | undefined) {
  return useQuery({
    queryKey: portKeys.detail(id ?? ''),
    queryFn: () => getPort(id as string),
    enabled: Boolean(id),
  });
}

export function usePortSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: portKeys.summaries(status),
    queryFn: () => listPortSummaries(status),
    staleTime: 60_000,
  });
}

export function usePortStatusStats() {
  return useQuery({
    queryKey: portKeys.stats(),
    queryFn: getPortStatusStats,
    staleTime: 30_000,
  });
}

export function useCreatePort() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: PortInput) => createPort(input),
    onSuccess: () => client.invalidateQueries({ queryKey: portKeys.all }),
  });
}

export function useUpdatePort(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: PortUpdateInput) => updatePort(id, input),
    onSuccess: (updated) => {
      client.setQueryData(portKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: portKeys.lists() });
    },
  });
}

export function useChangePortStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changePortStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: portKeys.all }),
  });
}

export function useDeletePort() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deletePort(id),
    onSuccess: () => client.invalidateQueries({ queryKey: portKeys.all }),
  });
}

export function useBulkCreatePorts() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: PortInput[]) => bulkCreatePorts(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: portKeys.all }),
  });
}

export function useExportPorts() {
  return useMutation({
    mutationFn: (filter: PortFilter) => exportPortsCsv(filter),
  });
}

export function usePortsByDeviceId(deviceId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...portKeys.all, 'by-device_id', deviceId, page, size],
    queryFn: () => listPortByDeviceId(deviceId as string, page, size),
    enabled: Boolean(deviceId),
  });
}

export function usePortsByCardId(cardId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...portKeys.all, 'by-card_id', cardId, page, size],
    queryFn: () => listPortByCardId(cardId as string, page, size),
    enabled: Boolean(cardId),
  });
}
