import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreatePowerFeeds,
  changePowerFeedStatus,
  createPowerFeed,
  deletePowerFeed,
  exportPowerFeedsCsv,
  getPowerFeed,
  getPowerFeedStatusStats,
  listPowerFeedSummaries,
  searchPowerFeeds,
  updatePowerFeed,
  listPowerFeedByRoomId,
} from './api';
import type { PowerFeedFilter, PowerFeedInput, PowerFeedUpdateInput } from './types';

export const powerFeedKeys = {
  all: ['powerFeed'] as const,
  lists: () => [...powerFeedKeys.all, 'list'] as const,
  list: (filter: PowerFeedFilter, page: number, size: number, sort?: string) =>
    [...powerFeedKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...powerFeedKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...powerFeedKeys.all, 'summaries', status] as const,
  stats: () => [...powerFeedKeys.all, 'stats'] as const,
};

export function usePowerFeedList(filter: PowerFeedFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: powerFeedKeys.list(filter, page, size, sort),
    queryFn: () => searchPowerFeeds(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function usePowerFeed(id: string | undefined) {
  return useQuery({
    queryKey: powerFeedKeys.detail(id ?? ''),
    queryFn: () => getPowerFeed(id as string),
    enabled: Boolean(id),
  });
}

export function usePowerFeedSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: powerFeedKeys.summaries(status),
    queryFn: () => listPowerFeedSummaries(status),
    staleTime: 60_000,
  });
}

export function usePowerFeedStatusStats() {
  return useQuery({
    queryKey: powerFeedKeys.stats(),
    queryFn: getPowerFeedStatusStats,
    staleTime: 30_000,
  });
}

export function useCreatePowerFeed() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: PowerFeedInput) => createPowerFeed(input),
    onSuccess: () => client.invalidateQueries({ queryKey: powerFeedKeys.all }),
  });
}

export function useUpdatePowerFeed(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: PowerFeedUpdateInput) => updatePowerFeed(id, input),
    onSuccess: (updated) => {
      client.setQueryData(powerFeedKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: powerFeedKeys.lists() });
    },
  });
}

export function useChangePowerFeedStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changePowerFeedStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: powerFeedKeys.all }),
  });
}

export function useDeletePowerFeed() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deletePowerFeed(id),
    onSuccess: () => client.invalidateQueries({ queryKey: powerFeedKeys.all }),
  });
}

export function useBulkCreatePowerFeeds() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: PowerFeedInput[]) => bulkCreatePowerFeeds(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: powerFeedKeys.all }),
  });
}

export function useExportPowerFeeds() {
  return useMutation({
    mutationFn: (filter: PowerFeedFilter) => exportPowerFeedsCsv(filter),
  });
}

export function usePowerFeedsByRoomId(roomId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...powerFeedKeys.all, 'by-room_id', roomId, page, size],
    queryFn: () => listPowerFeedByRoomId(roomId as string, page, size),
    enabled: Boolean(roomId),
  });
}
