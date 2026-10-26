import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateImportBatchs,
  changeImportBatchStatus,
  createImportBatch,
  deleteImportBatch,
  exportImportBatchsCsv,
  getImportBatch,
  getImportBatchStatusStats,
  listImportBatchSummaries,
  searchImportBatchs,
  updateImportBatch,

} from './api';
import type { ImportBatchFilter, ImportBatchInput, ImportBatchUpdateInput } from './types';

export const importBatchKeys = {
  all: ['importBatch'] as const,
  lists: () => [...importBatchKeys.all, 'list'] as const,
  list: (filter: ImportBatchFilter, page: number, size: number, sort?: string) =>
    [...importBatchKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...importBatchKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...importBatchKeys.all, 'summaries', status] as const,
  stats: () => [...importBatchKeys.all, 'stats'] as const,
};

export function useImportBatchList(filter: ImportBatchFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: importBatchKeys.list(filter, page, size, sort),
    queryFn: () => searchImportBatchs(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useImportBatch(id: string | undefined) {
  return useQuery({
    queryKey: importBatchKeys.detail(id ?? ''),
    queryFn: () => getImportBatch(id as string),
    enabled: Boolean(id),
  });
}

export function useImportBatchSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: importBatchKeys.summaries(status),
    queryFn: () => listImportBatchSummaries(status),
    staleTime: 60_000,
  });
}

export function useImportBatchStatusStats() {
  return useQuery({
    queryKey: importBatchKeys.stats(),
    queryFn: getImportBatchStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateImportBatch() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ImportBatchInput) => createImportBatch(input),
    onSuccess: () => client.invalidateQueries({ queryKey: importBatchKeys.all }),
  });
}

export function useUpdateImportBatch(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ImportBatchUpdateInput) => updateImportBatch(id, input),
    onSuccess: (updated) => {
      client.setQueryData(importBatchKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: importBatchKeys.lists() });
    },
  });
}

export function useChangeImportBatchStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeImportBatchStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: importBatchKeys.all }),
  });
}

export function useDeleteImportBatch() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteImportBatch(id),
    onSuccess: () => client.invalidateQueries({ queryKey: importBatchKeys.all }),
  });
}

export function useBulkCreateImportBatchs() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: ImportBatchInput[]) => bulkCreateImportBatchs(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: importBatchKeys.all }),
  });
}

export function useExportImportBatchs() {
  return useMutation({
    mutationFn: (filter: ImportBatchFilter) => exportImportBatchsCsv(filter),
  });
}
