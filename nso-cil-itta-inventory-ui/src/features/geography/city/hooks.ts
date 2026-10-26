import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateCitys,
  changeCityStatus,
  createCity,
  deleteCity,
  exportCitysCsv,
  getCity,
  getCityStatusStats,
  listCitySummaries,
  searchCitys,
  updateCity,
  listCityByRegionId,
} from './api';
import type { CityFilter, CityInput, CityUpdateInput } from './types';

export const cityKeys = {
  all: ['city'] as const,
  lists: () => [...cityKeys.all, 'list'] as const,
  list: (filter: CityFilter, page: number, size: number, sort?: string) =>
    [...cityKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...cityKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...cityKeys.all, 'summaries', status] as const,
  stats: () => [...cityKeys.all, 'stats'] as const,
};

export function useCityList(filter: CityFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: cityKeys.list(filter, page, size, sort),
    queryFn: () => searchCitys(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useCity(id: string | undefined) {
  return useQuery({
    queryKey: cityKeys.detail(id ?? ''),
    queryFn: () => getCity(id as string),
    enabled: Boolean(id),
  });
}

export function useCitySummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: cityKeys.summaries(status),
    queryFn: () => listCitySummaries(status),
    staleTime: 60_000,
  });
}

export function useCityStatusStats() {
  return useQuery({
    queryKey: cityKeys.stats(),
    queryFn: getCityStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateCity() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: CityInput) => createCity(input),
    onSuccess: () => client.invalidateQueries({ queryKey: cityKeys.all }),
  });
}

export function useUpdateCity(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: CityUpdateInput) => updateCity(id, input),
    onSuccess: (updated) => {
      client.setQueryData(cityKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: cityKeys.lists() });
    },
  });
}

export function useChangeCityStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeCityStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: cityKeys.all }),
  });
}

export function useDeleteCity() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteCity(id),
    onSuccess: () => client.invalidateQueries({ queryKey: cityKeys.all }),
  });
}

export function useBulkCreateCitys() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: CityInput[]) => bulkCreateCitys(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: cityKeys.all }),
  });
}

export function useExportCitys() {
  return useMutation({
    mutationFn: (filter: CityFilter) => exportCitysCsv(filter),
  });
}

export function useCitysByRegionId(regionId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...cityKeys.all, 'by-region_id', regionId, page, size],
    queryFn: () => listCityByRegionId(regionId as string, page, size),
    enabled: Boolean(regionId),
  });
}
