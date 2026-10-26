import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreatePowerSupplys,
  changePowerSupplyStatus,
  createPowerSupply,
  deletePowerSupply,
  exportPowerSupplysCsv,
  getPowerSupply,
  getPowerSupplyStatusStats,
  listPowerSupplySummaries,
  searchPowerSupplys,
  updatePowerSupply,
  listPowerSupplyByChassisId,
  listPowerSupplyByFeedId,
} from './api';
import type { PowerSupplyFilter, PowerSupplyInput, PowerSupplyUpdateInput } from './types';

export const powerSupplyKeys = {
  all: ['powerSupply'] as const,
  lists: () => [...powerSupplyKeys.all, 'list'] as const,
  list: (filter: PowerSupplyFilter, page: number, size: number, sort?: string) =>
    [...powerSupplyKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...powerSupplyKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...powerSupplyKeys.all, 'summaries', status] as const,
  stats: () => [...powerSupplyKeys.all, 'stats'] as const,
};

export function usePowerSupplyList(filter: PowerSupplyFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: powerSupplyKeys.list(filter, page, size, sort),
    queryFn: () => searchPowerSupplys(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function usePowerSupply(id: string | undefined) {
  return useQuery({
    queryKey: powerSupplyKeys.detail(id ?? ''),
    queryFn: () => getPowerSupply(id as string),
    enabled: Boolean(id),
  });
}

export function usePowerSupplySummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: powerSupplyKeys.summaries(status),
    queryFn: () => listPowerSupplySummaries(status),
    staleTime: 60_000,
  });
}

export function usePowerSupplyStatusStats() {
  return useQuery({
    queryKey: powerSupplyKeys.stats(),
    queryFn: getPowerSupplyStatusStats,
    staleTime: 30_000,
  });
}

export function useCreatePowerSupply() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: PowerSupplyInput) => createPowerSupply(input),
    onSuccess: () => client.invalidateQueries({ queryKey: powerSupplyKeys.all }),
  });
}

export function useUpdatePowerSupply(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: PowerSupplyUpdateInput) => updatePowerSupply(id, input),
    onSuccess: (updated) => {
      client.setQueryData(powerSupplyKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: powerSupplyKeys.lists() });
    },
  });
}

export function useChangePowerSupplyStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changePowerSupplyStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: powerSupplyKeys.all }),
  });
}

export function useDeletePowerSupply() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deletePowerSupply(id),
    onSuccess: () => client.invalidateQueries({ queryKey: powerSupplyKeys.all }),
  });
}

export function useBulkCreatePowerSupplys() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: PowerSupplyInput[]) => bulkCreatePowerSupplys(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: powerSupplyKeys.all }),
  });
}

export function useExportPowerSupplys() {
  return useMutation({
    mutationFn: (filter: PowerSupplyFilter) => exportPowerSupplysCsv(filter),
  });
}

export function usePowerSupplysByChassisId(chassisId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...powerSupplyKeys.all, 'by-chassis_id', chassisId, page, size],
    queryFn: () => listPowerSupplyByChassisId(chassisId as string, page, size),
    enabled: Boolean(chassisId),
  });
}

export function usePowerSupplysByFeedId(feedId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...powerSupplyKeys.all, 'by-feed_id', feedId, page, size],
    queryFn: () => listPowerSupplyByFeedId(feedId as string, page, size),
    enabled: Boolean(feedId),
  });
}
