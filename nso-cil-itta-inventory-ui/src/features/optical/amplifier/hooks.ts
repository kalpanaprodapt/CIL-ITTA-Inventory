import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateAmplifiers,
  changeAmplifierStatus,
  createAmplifier,
  deleteAmplifier,
  exportAmplifiersCsv,
  getAmplifier,
  getAmplifierStatusStats,
  listAmplifierSummaries,
  searchAmplifiers,
  updateAmplifier,
  listAmplifierBySystemId,
} from './api';
import type { AmplifierFilter, AmplifierInput, AmplifierUpdateInput } from './types';

export const amplifierKeys = {
  all: ['amplifier'] as const,
  lists: () => [...amplifierKeys.all, 'list'] as const,
  list: (filter: AmplifierFilter, page: number, size: number, sort?: string) =>
    [...amplifierKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...amplifierKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...amplifierKeys.all, 'summaries', status] as const,
  stats: () => [...amplifierKeys.all, 'stats'] as const,
};

export function useAmplifierList(filter: AmplifierFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: amplifierKeys.list(filter, page, size, sort),
    queryFn: () => searchAmplifiers(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useAmplifier(id: string | undefined) {
  return useQuery({
    queryKey: amplifierKeys.detail(id ?? ''),
    queryFn: () => getAmplifier(id as string),
    enabled: Boolean(id),
  });
}

export function useAmplifierSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: amplifierKeys.summaries(status),
    queryFn: () => listAmplifierSummaries(status),
    staleTime: 60_000,
  });
}

export function useAmplifierStatusStats() {
  return useQuery({
    queryKey: amplifierKeys.stats(),
    queryFn: getAmplifierStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateAmplifier() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: AmplifierInput) => createAmplifier(input),
    onSuccess: () => client.invalidateQueries({ queryKey: amplifierKeys.all }),
  });
}

export function useUpdateAmplifier(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: AmplifierUpdateInput) => updateAmplifier(id, input),
    onSuccess: (updated) => {
      client.setQueryData(amplifierKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: amplifierKeys.lists() });
    },
  });
}

export function useChangeAmplifierStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeAmplifierStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: amplifierKeys.all }),
  });
}

export function useDeleteAmplifier() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteAmplifier(id),
    onSuccess: () => client.invalidateQueries({ queryKey: amplifierKeys.all }),
  });
}

export function useBulkCreateAmplifiers() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: AmplifierInput[]) => bulkCreateAmplifiers(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: amplifierKeys.all }),
  });
}

export function useExportAmplifiers() {
  return useMutation({
    mutationFn: (filter: AmplifierFilter) => exportAmplifiersCsv(filter),
  });
}

export function useAmplifiersBySystemId(systemId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...amplifierKeys.all, 'by-system_id', systemId, page, size],
    queryFn: () => listAmplifierBySystemId(systemId as string, page, size),
    enabled: Boolean(systemId),
  });
}
