import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateSubInterfaces,
  changeSubInterfaceStatus,
  createSubInterface,
  deleteSubInterface,
  exportSubInterfacesCsv,
  getSubInterface,
  getSubInterfaceStatusStats,
  listSubInterfaceSummaries,
  searchSubInterfaces,
  updateSubInterface,
  listSubInterfaceByInterfaceId,
} from './api';
import type { SubInterfaceFilter, SubInterfaceInput, SubInterfaceUpdateInput } from './types';

export const subInterfaceKeys = {
  all: ['subInterface'] as const,
  lists: () => [...subInterfaceKeys.all, 'list'] as const,
  list: (filter: SubInterfaceFilter, page: number, size: number, sort?: string) =>
    [...subInterfaceKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...subInterfaceKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...subInterfaceKeys.all, 'summaries', status] as const,
  stats: () => [...subInterfaceKeys.all, 'stats'] as const,
};

export function useSubInterfaceList(filter: SubInterfaceFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: subInterfaceKeys.list(filter, page, size, sort),
    queryFn: () => searchSubInterfaces(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useSubInterface(id: string | undefined) {
  return useQuery({
    queryKey: subInterfaceKeys.detail(id ?? ''),
    queryFn: () => getSubInterface(id as string),
    enabled: Boolean(id),
  });
}

export function useSubInterfaceSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: subInterfaceKeys.summaries(status),
    queryFn: () => listSubInterfaceSummaries(status),
    staleTime: 60_000,
  });
}

export function useSubInterfaceStatusStats() {
  return useQuery({
    queryKey: subInterfaceKeys.stats(),
    queryFn: getSubInterfaceStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateSubInterface() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: SubInterfaceInput) => createSubInterface(input),
    onSuccess: () => client.invalidateQueries({ queryKey: subInterfaceKeys.all }),
  });
}

export function useUpdateSubInterface(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: SubInterfaceUpdateInput) => updateSubInterface(id, input),
    onSuccess: (updated) => {
      client.setQueryData(subInterfaceKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: subInterfaceKeys.lists() });
    },
  });
}

export function useChangeSubInterfaceStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeSubInterfaceStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: subInterfaceKeys.all }),
  });
}

export function useDeleteSubInterface() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteSubInterface(id),
    onSuccess: () => client.invalidateQueries({ queryKey: subInterfaceKeys.all }),
  });
}

export function useBulkCreateSubInterfaces() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: SubInterfaceInput[]) => bulkCreateSubInterfaces(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: subInterfaceKeys.all }),
  });
}

export function useExportSubInterfaces() {
  return useMutation({
    mutationFn: (filter: SubInterfaceFilter) => exportSubInterfacesCsv(filter),
  });
}

export function useSubInterfacesByInterfaceId(interfaceId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...subInterfaceKeys.all, 'by-interface_id', interfaceId, page, size],
    queryFn: () => listSubInterfaceByInterfaceId(interfaceId as string, page, size),
    enabled: Boolean(interfaceId),
  });
}
