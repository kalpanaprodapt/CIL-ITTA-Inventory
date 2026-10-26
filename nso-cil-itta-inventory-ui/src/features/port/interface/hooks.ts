import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateInterfaces,
  changeInterfaceStatus,
  createInterface,
  deleteInterface,
  exportInterfacesCsv,
  getInterface,
  getInterfaceStatusStats,
  listInterfaceSummaries,
  searchInterfaces,
  updateInterface,
  listInterfaceByPortId,
} from './api';
import type { InterfaceFilter, InterfaceInput, InterfaceUpdateInput } from './types';

export const interfaceKeys = {
  all: ['interface'] as const,
  lists: () => [...interfaceKeys.all, 'list'] as const,
  list: (filter: InterfaceFilter, page: number, size: number, sort?: string) =>
    [...interfaceKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...interfaceKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...interfaceKeys.all, 'summaries', status] as const,
  stats: () => [...interfaceKeys.all, 'stats'] as const,
};

export function useInterfaceList(filter: InterfaceFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: interfaceKeys.list(filter, page, size, sort),
    queryFn: () => searchInterfaces(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useInterface(id: string | undefined) {
  return useQuery({
    queryKey: interfaceKeys.detail(id ?? ''),
    queryFn: () => getInterface(id as string),
    enabled: Boolean(id),
  });
}

export function useInterfaceSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: interfaceKeys.summaries(status),
    queryFn: () => listInterfaceSummaries(status),
    staleTime: 60_000,
  });
}

export function useInterfaceStatusStats() {
  return useQuery({
    queryKey: interfaceKeys.stats(),
    queryFn: getInterfaceStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateInterface() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: InterfaceInput) => createInterface(input),
    onSuccess: () => client.invalidateQueries({ queryKey: interfaceKeys.all }),
  });
}

export function useUpdateInterface(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: InterfaceUpdateInput) => updateInterface(id, input),
    onSuccess: (updated) => {
      client.setQueryData(interfaceKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: interfaceKeys.lists() });
    },
  });
}

export function useChangeInterfaceStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeInterfaceStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: interfaceKeys.all }),
  });
}

export function useDeleteInterface() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteInterface(id),
    onSuccess: () => client.invalidateQueries({ queryKey: interfaceKeys.all }),
  });
}

export function useBulkCreateInterfaces() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: InterfaceInput[]) => bulkCreateInterfaces(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: interfaceKeys.all }),
  });
}

export function useExportInterfaces() {
  return useMutation({
    mutationFn: (filter: InterfaceFilter) => exportInterfacesCsv(filter),
  });
}

export function useInterfacesByPortId(portId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...interfaceKeys.all, 'by-port_id', portId, page, size],
    queryFn: () => listInterfaceByPortId(portId as string, page, size),
    enabled: Boolean(portId),
  });
}
