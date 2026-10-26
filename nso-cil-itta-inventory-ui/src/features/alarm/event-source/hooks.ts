import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateEventSources,
  changeEventSourceStatus,
  createEventSource,
  deleteEventSource,
  exportEventSourcesCsv,
  getEventSource,
  getEventSourceStatusStats,
  listEventSourceSummaries,
  searchEventSources,
  updateEventSource,

} from './api';
import type { EventSourceFilter, EventSourceInput, EventSourceUpdateInput } from './types';

export const eventSourceKeys = {
  all: ['eventSource'] as const,
  lists: () => [...eventSourceKeys.all, 'list'] as const,
  list: (filter: EventSourceFilter, page: number, size: number, sort?: string) =>
    [...eventSourceKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...eventSourceKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...eventSourceKeys.all, 'summaries', status] as const,
  stats: () => [...eventSourceKeys.all, 'stats'] as const,
};

export function useEventSourceList(filter: EventSourceFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: eventSourceKeys.list(filter, page, size, sort),
    queryFn: () => searchEventSources(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useEventSource(id: string | undefined) {
  return useQuery({
    queryKey: eventSourceKeys.detail(id ?? ''),
    queryFn: () => getEventSource(id as string),
    enabled: Boolean(id),
  });
}

export function useEventSourceSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: eventSourceKeys.summaries(status),
    queryFn: () => listEventSourceSummaries(status),
    staleTime: 60_000,
  });
}

export function useEventSourceStatusStats() {
  return useQuery({
    queryKey: eventSourceKeys.stats(),
    queryFn: getEventSourceStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateEventSource() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: EventSourceInput) => createEventSource(input),
    onSuccess: () => client.invalidateQueries({ queryKey: eventSourceKeys.all }),
  });
}

export function useUpdateEventSource(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: EventSourceUpdateInput) => updateEventSource(id, input),
    onSuccess: (updated) => {
      client.setQueryData(eventSourceKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: eventSourceKeys.lists() });
    },
  });
}

export function useChangeEventSourceStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeEventSourceStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: eventSourceKeys.all }),
  });
}

export function useDeleteEventSource() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteEventSource(id),
    onSuccess: () => client.invalidateQueries({ queryKey: eventSourceKeys.all }),
  });
}

export function useBulkCreateEventSources() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: EventSourceInput[]) => bulkCreateEventSources(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: eventSourceKeys.all }),
  });
}

export function useExportEventSources() {
  return useMutation({
    mutationFn: (filter: EventSourceFilter) => exportEventSourcesCsv(filter),
  });
}
