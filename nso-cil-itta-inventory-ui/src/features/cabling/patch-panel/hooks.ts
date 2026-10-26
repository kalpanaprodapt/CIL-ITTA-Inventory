import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreatePatchPanels,
  changePatchPanelStatus,
  createPatchPanel,
  deletePatchPanel,
  exportPatchPanelsCsv,
  getPatchPanel,
  getPatchPanelStatusStats,
  listPatchPanelSummaries,
  searchPatchPanels,
  updatePatchPanel,
  listPatchPanelByRoomId,
  listPatchPanelByRackId,
} from './api';
import type { PatchPanelFilter, PatchPanelInput, PatchPanelUpdateInput } from './types';

export const patchPanelKeys = {
  all: ['patchPanel'] as const,
  lists: () => [...patchPanelKeys.all, 'list'] as const,
  list: (filter: PatchPanelFilter, page: number, size: number, sort?: string) =>
    [...patchPanelKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...patchPanelKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...patchPanelKeys.all, 'summaries', status] as const,
  stats: () => [...patchPanelKeys.all, 'stats'] as const,
};

export function usePatchPanelList(filter: PatchPanelFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: patchPanelKeys.list(filter, page, size, sort),
    queryFn: () => searchPatchPanels(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function usePatchPanel(id: string | undefined) {
  return useQuery({
    queryKey: patchPanelKeys.detail(id ?? ''),
    queryFn: () => getPatchPanel(id as string),
    enabled: Boolean(id),
  });
}

export function usePatchPanelSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: patchPanelKeys.summaries(status),
    queryFn: () => listPatchPanelSummaries(status),
    staleTime: 60_000,
  });
}

export function usePatchPanelStatusStats() {
  return useQuery({
    queryKey: patchPanelKeys.stats(),
    queryFn: getPatchPanelStatusStats,
    staleTime: 30_000,
  });
}

export function useCreatePatchPanel() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: PatchPanelInput) => createPatchPanel(input),
    onSuccess: () => client.invalidateQueries({ queryKey: patchPanelKeys.all }),
  });
}

export function useUpdatePatchPanel(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: PatchPanelUpdateInput) => updatePatchPanel(id, input),
    onSuccess: (updated) => {
      client.setQueryData(patchPanelKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: patchPanelKeys.lists() });
    },
  });
}

export function useChangePatchPanelStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changePatchPanelStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: patchPanelKeys.all }),
  });
}

export function useDeletePatchPanel() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deletePatchPanel(id),
    onSuccess: () => client.invalidateQueries({ queryKey: patchPanelKeys.all }),
  });
}

export function useBulkCreatePatchPanels() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: PatchPanelInput[]) => bulkCreatePatchPanels(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: patchPanelKeys.all }),
  });
}

export function useExportPatchPanels() {
  return useMutation({
    mutationFn: (filter: PatchPanelFilter) => exportPatchPanelsCsv(filter),
  });
}

export function usePatchPanelsByRoomId(roomId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...patchPanelKeys.all, 'by-room_id', roomId, page, size],
    queryFn: () => listPatchPanelByRoomId(roomId as string, page, size),
    enabled: Boolean(roomId),
  });
}

export function usePatchPanelsByRackId(rackId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...patchPanelKeys.all, 'by-rack_id', rackId, page, size],
    queryFn: () => listPatchPanelByRackId(rackId as string, page, size),
    enabled: Boolean(rackId),
  });
}
