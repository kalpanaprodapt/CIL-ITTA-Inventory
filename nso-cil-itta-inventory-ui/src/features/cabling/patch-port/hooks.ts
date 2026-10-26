import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreatePatchPorts,
  changePatchPortStatus,
  createPatchPort,
  deletePatchPort,
  exportPatchPortsCsv,
  getPatchPort,
  getPatchPortStatusStats,
  listPatchPortSummaries,
  searchPatchPorts,
  updatePatchPort,
  listPatchPortByPanelId,
  listPatchPortByConnectedCableId,
} from './api';
import type { PatchPortFilter, PatchPortInput, PatchPortUpdateInput } from './types';

export const patchPortKeys = {
  all: ['patchPort'] as const,
  lists: () => [...patchPortKeys.all, 'list'] as const,
  list: (filter: PatchPortFilter, page: number, size: number, sort?: string) =>
    [...patchPortKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...patchPortKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...patchPortKeys.all, 'summaries', status] as const,
  stats: () => [...patchPortKeys.all, 'stats'] as const,
};

export function usePatchPortList(filter: PatchPortFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: patchPortKeys.list(filter, page, size, sort),
    queryFn: () => searchPatchPorts(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function usePatchPort(id: string | undefined) {
  return useQuery({
    queryKey: patchPortKeys.detail(id ?? ''),
    queryFn: () => getPatchPort(id as string),
    enabled: Boolean(id),
  });
}

export function usePatchPortSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: patchPortKeys.summaries(status),
    queryFn: () => listPatchPortSummaries(status),
    staleTime: 60_000,
  });
}

export function usePatchPortStatusStats() {
  return useQuery({
    queryKey: patchPortKeys.stats(),
    queryFn: getPatchPortStatusStats,
    staleTime: 30_000,
  });
}

export function useCreatePatchPort() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: PatchPortInput) => createPatchPort(input),
    onSuccess: () => client.invalidateQueries({ queryKey: patchPortKeys.all }),
  });
}

export function useUpdatePatchPort(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: PatchPortUpdateInput) => updatePatchPort(id, input),
    onSuccess: (updated) => {
      client.setQueryData(patchPortKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: patchPortKeys.lists() });
    },
  });
}

export function useChangePatchPortStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changePatchPortStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: patchPortKeys.all }),
  });
}

export function useDeletePatchPort() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deletePatchPort(id),
    onSuccess: () => client.invalidateQueries({ queryKey: patchPortKeys.all }),
  });
}

export function useBulkCreatePatchPorts() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: PatchPortInput[]) => bulkCreatePatchPorts(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: patchPortKeys.all }),
  });
}

export function useExportPatchPorts() {
  return useMutation({
    mutationFn: (filter: PatchPortFilter) => exportPatchPortsCsv(filter),
  });
}

export function usePatchPortsByPanelId(panelId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...patchPortKeys.all, 'by-panel_id', panelId, page, size],
    queryFn: () => listPatchPortByPanelId(panelId as string, page, size),
    enabled: Boolean(panelId),
  });
}

export function usePatchPortsByConnectedCableId(connectedCableId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...patchPortKeys.all, 'by-connected_cable_id', connectedCableId, page, size],
    queryFn: () => listPatchPortByConnectedCableId(connectedCableId as string, page, size),
    enabled: Boolean(connectedCableId),
  });
}
