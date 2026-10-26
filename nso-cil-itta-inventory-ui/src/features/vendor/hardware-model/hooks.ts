import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateHardwareModels,
  changeHardwareModelStatus,
  createHardwareModel,
  deleteHardwareModel,
  exportHardwareModelsCsv,
  getHardwareModel,
  getHardwareModelStatusStats,
  listHardwareModelSummaries,
  searchHardwareModels,
  updateHardwareModel,
  listHardwareModelByVendorId,
} from './api';
import type { HardwareModelFilter, HardwareModelInput, HardwareModelUpdateInput } from './types';

export const hardwareModelKeys = {
  all: ['hardwareModel'] as const,
  lists: () => [...hardwareModelKeys.all, 'list'] as const,
  list: (filter: HardwareModelFilter, page: number, size: number, sort?: string) =>
    [...hardwareModelKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...hardwareModelKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...hardwareModelKeys.all, 'summaries', status] as const,
  stats: () => [...hardwareModelKeys.all, 'stats'] as const,
};

export function useHardwareModelList(filter: HardwareModelFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: hardwareModelKeys.list(filter, page, size, sort),
    queryFn: () => searchHardwareModels(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useHardwareModel(id: string | undefined) {
  return useQuery({
    queryKey: hardwareModelKeys.detail(id ?? ''),
    queryFn: () => getHardwareModel(id as string),
    enabled: Boolean(id),
  });
}

export function useHardwareModelSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: hardwareModelKeys.summaries(status),
    queryFn: () => listHardwareModelSummaries(status),
    staleTime: 60_000,
  });
}

export function useHardwareModelStatusStats() {
  return useQuery({
    queryKey: hardwareModelKeys.stats(),
    queryFn: getHardwareModelStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateHardwareModel() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: HardwareModelInput) => createHardwareModel(input),
    onSuccess: () => client.invalidateQueries({ queryKey: hardwareModelKeys.all }),
  });
}

export function useUpdateHardwareModel(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: HardwareModelUpdateInput) => updateHardwareModel(id, input),
    onSuccess: (updated) => {
      client.setQueryData(hardwareModelKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: hardwareModelKeys.lists() });
    },
  });
}

export function useChangeHardwareModelStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeHardwareModelStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: hardwareModelKeys.all }),
  });
}

export function useDeleteHardwareModel() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteHardwareModel(id),
    onSuccess: () => client.invalidateQueries({ queryKey: hardwareModelKeys.all }),
  });
}

export function useBulkCreateHardwareModels() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: HardwareModelInput[]) => bulkCreateHardwareModels(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: hardwareModelKeys.all }),
  });
}

export function useExportHardwareModels() {
  return useMutation({
    mutationFn: (filter: HardwareModelFilter) => exportHardwareModelsCsv(filter),
  });
}

export function useHardwareModelsByVendorId(vendorId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...hardwareModelKeys.all, 'by-vendor_id', vendorId, page, size],
    queryFn: () => listHardwareModelByVendorId(vendorId as string, page, size),
    enabled: Boolean(vendorId),
  });
}
