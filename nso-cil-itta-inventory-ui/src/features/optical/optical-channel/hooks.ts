import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateOpticalChannels,
  changeOpticalChannelStatus,
  createOpticalChannel,
  deleteOpticalChannel,
  exportOpticalChannelsCsv,
  getOpticalChannel,
  getOpticalChannelStatusStats,
  listOpticalChannelSummaries,
  searchOpticalChannels,
  updateOpticalChannel,
  listOpticalChannelBySystemId,
} from './api';
import type { OpticalChannelFilter, OpticalChannelInput, OpticalChannelUpdateInput } from './types';

export const opticalChannelKeys = {
  all: ['opticalChannel'] as const,
  lists: () => [...opticalChannelKeys.all, 'list'] as const,
  list: (filter: OpticalChannelFilter, page: number, size: number, sort?: string) =>
    [...opticalChannelKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...opticalChannelKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...opticalChannelKeys.all, 'summaries', status] as const,
  stats: () => [...opticalChannelKeys.all, 'stats'] as const,
};

export function useOpticalChannelList(filter: OpticalChannelFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: opticalChannelKeys.list(filter, page, size, sort),
    queryFn: () => searchOpticalChannels(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useOpticalChannel(id: string | undefined) {
  return useQuery({
    queryKey: opticalChannelKeys.detail(id ?? ''),
    queryFn: () => getOpticalChannel(id as string),
    enabled: Boolean(id),
  });
}

export function useOpticalChannelSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: opticalChannelKeys.summaries(status),
    queryFn: () => listOpticalChannelSummaries(status),
    staleTime: 60_000,
  });
}

export function useOpticalChannelStatusStats() {
  return useQuery({
    queryKey: opticalChannelKeys.stats(),
    queryFn: getOpticalChannelStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateOpticalChannel() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: OpticalChannelInput) => createOpticalChannel(input),
    onSuccess: () => client.invalidateQueries({ queryKey: opticalChannelKeys.all }),
  });
}

export function useUpdateOpticalChannel(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: OpticalChannelUpdateInput) => updateOpticalChannel(id, input),
    onSuccess: (updated) => {
      client.setQueryData(opticalChannelKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: opticalChannelKeys.lists() });
    },
  });
}

export function useChangeOpticalChannelStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeOpticalChannelStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: opticalChannelKeys.all }),
  });
}

export function useDeleteOpticalChannel() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteOpticalChannel(id),
    onSuccess: () => client.invalidateQueries({ queryKey: opticalChannelKeys.all }),
  });
}

export function useBulkCreateOpticalChannels() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: OpticalChannelInput[]) => bulkCreateOpticalChannels(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: opticalChannelKeys.all }),
  });
}

export function useExportOpticalChannels() {
  return useMutation({
    mutationFn: (filter: OpticalChannelFilter) => exportOpticalChannelsCsv(filter),
  });
}

export function useOpticalChannelsBySystemId(systemId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...opticalChannelKeys.all, 'by-system_id', systemId, page, size],
    queryFn: () => listOpticalChannelBySystemId(systemId as string, page, size),
    enabled: Boolean(systemId),
  });
}
