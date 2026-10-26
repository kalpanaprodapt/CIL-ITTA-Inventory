import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateRadioUnits,
  changeRadioUnitStatus,
  createRadioUnit,
  deleteRadioUnit,
  exportRadioUnitsCsv,
  getRadioUnit,
  getRadioUnitStatusStats,
  listRadioUnitSummaries,
  searchRadioUnits,
  updateRadioUnit,
  listRadioUnitBySiteId,
} from './api';
import type { RadioUnitFilter, RadioUnitInput, RadioUnitUpdateInput } from './types';

export const radioUnitKeys = {
  all: ['radioUnit'] as const,
  lists: () => [...radioUnitKeys.all, 'list'] as const,
  list: (filter: RadioUnitFilter, page: number, size: number, sort?: string) =>
    [...radioUnitKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...radioUnitKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...radioUnitKeys.all, 'summaries', status] as const,
  stats: () => [...radioUnitKeys.all, 'stats'] as const,
};

export function useRadioUnitList(filter: RadioUnitFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: radioUnitKeys.list(filter, page, size, sort),
    queryFn: () => searchRadioUnits(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useRadioUnit(id: string | undefined) {
  return useQuery({
    queryKey: radioUnitKeys.detail(id ?? ''),
    queryFn: () => getRadioUnit(id as string),
    enabled: Boolean(id),
  });
}

export function useRadioUnitSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: radioUnitKeys.summaries(status),
    queryFn: () => listRadioUnitSummaries(status),
    staleTime: 60_000,
  });
}

export function useRadioUnitStatusStats() {
  return useQuery({
    queryKey: radioUnitKeys.stats(),
    queryFn: getRadioUnitStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateRadioUnit() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: RadioUnitInput) => createRadioUnit(input),
    onSuccess: () => client.invalidateQueries({ queryKey: radioUnitKeys.all }),
  });
}

export function useUpdateRadioUnit(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: RadioUnitUpdateInput) => updateRadioUnit(id, input),
    onSuccess: (updated) => {
      client.setQueryData(radioUnitKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: radioUnitKeys.lists() });
    },
  });
}

export function useChangeRadioUnitStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeRadioUnitStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: radioUnitKeys.all }),
  });
}

export function useDeleteRadioUnit() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteRadioUnit(id),
    onSuccess: () => client.invalidateQueries({ queryKey: radioUnitKeys.all }),
  });
}

export function useBulkCreateRadioUnits() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: RadioUnitInput[]) => bulkCreateRadioUnits(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: radioUnitKeys.all }),
  });
}

export function useExportRadioUnits() {
  return useMutation({
    mutationFn: (filter: RadioUnitFilter) => exportRadioUnitsCsv(filter),
  });
}

export function useRadioUnitsBySiteId(siteId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...radioUnitKeys.all, 'by-site_id', siteId, page, size],
    queryFn: () => listRadioUnitBySiteId(siteId as string, page, size),
    enabled: Boolean(siteId),
  });
}
