import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreatePathTraces,
  changePathTraceStatus,
  createPathTrace,
  deletePathTrace,
  exportPathTracesCsv,
  getPathTrace,
  getPathTraceStatusStats,
  listPathTraceSummaries,
  searchPathTraces,
  updatePathTrace,

} from './api';
import type { PathTraceFilter, PathTraceInput, PathTraceUpdateInput } from './types';

export const pathTraceKeys = {
  all: ['pathTrace'] as const,
  lists: () => [...pathTraceKeys.all, 'list'] as const,
  list: (filter: PathTraceFilter, page: number, size: number, sort?: string) =>
    [...pathTraceKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...pathTraceKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...pathTraceKeys.all, 'summaries', status] as const,
  stats: () => [...pathTraceKeys.all, 'stats'] as const,
};

export function usePathTraceList(filter: PathTraceFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: pathTraceKeys.list(filter, page, size, sort),
    queryFn: () => searchPathTraces(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function usePathTrace(id: string | undefined) {
  return useQuery({
    queryKey: pathTraceKeys.detail(id ?? ''),
    queryFn: () => getPathTrace(id as string),
    enabled: Boolean(id),
  });
}

export function usePathTraceSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: pathTraceKeys.summaries(status),
    queryFn: () => listPathTraceSummaries(status),
    staleTime: 60_000,
  });
}

export function usePathTraceStatusStats() {
  return useQuery({
    queryKey: pathTraceKeys.stats(),
    queryFn: getPathTraceStatusStats,
    staleTime: 30_000,
  });
}

export function useCreatePathTrace() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: PathTraceInput) => createPathTrace(input),
    onSuccess: () => client.invalidateQueries({ queryKey: pathTraceKeys.all }),
  });
}

export function useUpdatePathTrace(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: PathTraceUpdateInput) => updatePathTrace(id, input),
    onSuccess: (updated) => {
      client.setQueryData(pathTraceKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: pathTraceKeys.lists() });
    },
  });
}

export function useChangePathTraceStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changePathTraceStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: pathTraceKeys.all }),
  });
}

export function useDeletePathTrace() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deletePathTrace(id),
    onSuccess: () => client.invalidateQueries({ queryKey: pathTraceKeys.all }),
  });
}

export function useBulkCreatePathTraces() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: PathTraceInput[]) => bulkCreatePathTraces(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: pathTraceKeys.all }),
  });
}

export function useExportPathTraces() {
  return useMutation({
    mutationFn: (filter: PathTraceFilter) => exportPathTracesCsv(filter),
  });
}
