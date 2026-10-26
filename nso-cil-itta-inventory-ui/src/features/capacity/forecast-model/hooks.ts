import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateForecastModels,
  changeForecastModelStatus,
  createForecastModel,
  deleteForecastModel,
  exportForecastModelsCsv,
  getForecastModel,
  getForecastModelStatusStats,
  listForecastModelSummaries,
  searchForecastModels,
  updateForecastModel,

} from './api';
import type { ForecastModelFilter, ForecastModelInput, ForecastModelUpdateInput } from './types';

export const forecastModelKeys = {
  all: ['forecastModel'] as const,
  lists: () => [...forecastModelKeys.all, 'list'] as const,
  list: (filter: ForecastModelFilter, page: number, size: number, sort?: string) =>
    [...forecastModelKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...forecastModelKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...forecastModelKeys.all, 'summaries', status] as const,
  stats: () => [...forecastModelKeys.all, 'stats'] as const,
};

export function useForecastModelList(filter: ForecastModelFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: forecastModelKeys.list(filter, page, size, sort),
    queryFn: () => searchForecastModels(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useForecastModel(id: string | undefined) {
  return useQuery({
    queryKey: forecastModelKeys.detail(id ?? ''),
    queryFn: () => getForecastModel(id as string),
    enabled: Boolean(id),
  });
}

export function useForecastModelSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: forecastModelKeys.summaries(status),
    queryFn: () => listForecastModelSummaries(status),
    staleTime: 60_000,
  });
}

export function useForecastModelStatusStats() {
  return useQuery({
    queryKey: forecastModelKeys.stats(),
    queryFn: getForecastModelStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateForecastModel() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ForecastModelInput) => createForecastModel(input),
    onSuccess: () => client.invalidateQueries({ queryKey: forecastModelKeys.all }),
  });
}

export function useUpdateForecastModel(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ForecastModelUpdateInput) => updateForecastModel(id, input),
    onSuccess: (updated) => {
      client.setQueryData(forecastModelKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: forecastModelKeys.lists() });
    },
  });
}

export function useChangeForecastModelStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeForecastModelStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: forecastModelKeys.all }),
  });
}

export function useDeleteForecastModel() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteForecastModel(id),
    onSuccess: () => client.invalidateQueries({ queryKey: forecastModelKeys.all }),
  });
}

export function useBulkCreateForecastModels() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: ForecastModelInput[]) => bulkCreateForecastModels(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: forecastModelKeys.all }),
  });
}

export function useExportForecastModels() {
  return useMutation({
    mutationFn: (filter: ForecastModelFilter) => exportForecastModelsCsv(filter),
  });
}
