import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateBgpSessions,
  changeBgpSessionStatus,
  createBgpSession,
  deleteBgpSession,
  exportBgpSessionsCsv,
  getBgpSession,
  getBgpSessionStatusStats,
  listBgpSessionSummaries,
  searchBgpSessions,
  updateBgpSession,
  listBgpSessionByLocalDeviceId,
} from './api';
import type { BgpSessionFilter, BgpSessionInput, BgpSessionUpdateInput } from './types';

export const bgpSessionKeys = {
  all: ['bgpSession'] as const,
  lists: () => [...bgpSessionKeys.all, 'list'] as const,
  list: (filter: BgpSessionFilter, page: number, size: number, sort?: string) =>
    [...bgpSessionKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...bgpSessionKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...bgpSessionKeys.all, 'summaries', status] as const,
  stats: () => [...bgpSessionKeys.all, 'stats'] as const,
};

export function useBgpSessionList(filter: BgpSessionFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: bgpSessionKeys.list(filter, page, size, sort),
    queryFn: () => searchBgpSessions(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useBgpSession(id: string | undefined) {
  return useQuery({
    queryKey: bgpSessionKeys.detail(id ?? ''),
    queryFn: () => getBgpSession(id as string),
    enabled: Boolean(id),
  });
}

export function useBgpSessionSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: bgpSessionKeys.summaries(status),
    queryFn: () => listBgpSessionSummaries(status),
    staleTime: 60_000,
  });
}

export function useBgpSessionStatusStats() {
  return useQuery({
    queryKey: bgpSessionKeys.stats(),
    queryFn: getBgpSessionStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateBgpSession() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: BgpSessionInput) => createBgpSession(input),
    onSuccess: () => client.invalidateQueries({ queryKey: bgpSessionKeys.all }),
  });
}

export function useUpdateBgpSession(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: BgpSessionUpdateInput) => updateBgpSession(id, input),
    onSuccess: (updated) => {
      client.setQueryData(bgpSessionKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: bgpSessionKeys.lists() });
    },
  });
}

export function useChangeBgpSessionStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeBgpSessionStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: bgpSessionKeys.all }),
  });
}

export function useDeleteBgpSession() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteBgpSession(id),
    onSuccess: () => client.invalidateQueries({ queryKey: bgpSessionKeys.all }),
  });
}

export function useBulkCreateBgpSessions() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: BgpSessionInput[]) => bulkCreateBgpSessions(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: bgpSessionKeys.all }),
  });
}

export function useExportBgpSessions() {
  return useMutation({
    mutationFn: (filter: BgpSessionFilter) => exportBgpSessionsCsv(filter),
  });
}

export function useBgpSessionsByLocalDeviceId(localDeviceId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...bgpSessionKeys.all, 'by-local_device_id', localDeviceId, page, size],
    queryFn: () => listBgpSessionByLocalDeviceId(localDeviceId as string, page, size),
    enabled: Boolean(localDeviceId),
  });
}
