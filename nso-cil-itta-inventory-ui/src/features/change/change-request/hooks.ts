import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateChangeRequests,
  changeChangeRequestStatus,
  createChangeRequest,
  deleteChangeRequest,
  exportChangeRequestsCsv,
  getChangeRequest,
  getChangeRequestStatusStats,
  listChangeRequestSummaries,
  searchChangeRequests,
  updateChangeRequest,

} from './api';
import type { ChangeRequestFilter, ChangeRequestInput, ChangeRequestUpdateInput } from './types';

export const changeRequestKeys = {
  all: ['changeRequest'] as const,
  lists: () => [...changeRequestKeys.all, 'list'] as const,
  list: (filter: ChangeRequestFilter, page: number, size: number, sort?: string) =>
    [...changeRequestKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...changeRequestKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...changeRequestKeys.all, 'summaries', status] as const,
  stats: () => [...changeRequestKeys.all, 'stats'] as const,
};

export function useChangeRequestList(filter: ChangeRequestFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: changeRequestKeys.list(filter, page, size, sort),
    queryFn: () => searchChangeRequests(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useChangeRequest(id: string | undefined) {
  return useQuery({
    queryKey: changeRequestKeys.detail(id ?? ''),
    queryFn: () => getChangeRequest(id as string),
    enabled: Boolean(id),
  });
}

export function useChangeRequestSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: changeRequestKeys.summaries(status),
    queryFn: () => listChangeRequestSummaries(status),
    staleTime: 60_000,
  });
}

export function useChangeRequestStatusStats() {
  return useQuery({
    queryKey: changeRequestKeys.stats(),
    queryFn: getChangeRequestStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateChangeRequest() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ChangeRequestInput) => createChangeRequest(input),
    onSuccess: () => client.invalidateQueries({ queryKey: changeRequestKeys.all }),
  });
}

export function useUpdateChangeRequest(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ChangeRequestUpdateInput) => updateChangeRequest(id, input),
    onSuccess: (updated) => {
      client.setQueryData(changeRequestKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: changeRequestKeys.lists() });
    },
  });
}

export function useChangeChangeRequestStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeChangeRequestStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: changeRequestKeys.all }),
  });
}

export function useDeleteChangeRequest() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteChangeRequest(id),
    onSuccess: () => client.invalidateQueries({ queryKey: changeRequestKeys.all }),
  });
}

export function useBulkCreateChangeRequests() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: ChangeRequestInput[]) => bulkCreateChangeRequests(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: changeRequestKeys.all }),
  });
}

export function useExportChangeRequests() {
  return useMutation({
    mutationFn: (filter: ChangeRequestFilter) => exportChangeRequestsCsv(filter),
  });
}
