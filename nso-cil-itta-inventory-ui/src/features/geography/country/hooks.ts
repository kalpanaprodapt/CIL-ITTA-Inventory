import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateCountrys,
  changeCountryStatus,
  createCountry,
  deleteCountry,
  exportCountrysCsv,
  getCountry,
  getCountryStatusStats,
  listCountrySummaries,
  searchCountrys,
  updateCountry,

} from './api';
import type { CountryFilter, CountryInput, CountryUpdateInput } from './types';

export const countryKeys = {
  all: ['country'] as const,
  lists: () => [...countryKeys.all, 'list'] as const,
  list: (filter: CountryFilter, page: number, size: number, sort?: string) =>
    [...countryKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...countryKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...countryKeys.all, 'summaries', status] as const,
  stats: () => [...countryKeys.all, 'stats'] as const,
};

export function useCountryList(filter: CountryFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: countryKeys.list(filter, page, size, sort),
    queryFn: () => searchCountrys(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useCountry(id: string | undefined) {
  return useQuery({
    queryKey: countryKeys.detail(id ?? ''),
    queryFn: () => getCountry(id as string),
    enabled: Boolean(id),
  });
}

export function useCountrySummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: countryKeys.summaries(status),
    queryFn: () => listCountrySummaries(status),
    staleTime: 60_000,
  });
}

export function useCountryStatusStats() {
  return useQuery({
    queryKey: countryKeys.stats(),
    queryFn: getCountryStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateCountry() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: CountryInput) => createCountry(input),
    onSuccess: () => client.invalidateQueries({ queryKey: countryKeys.all }),
  });
}

export function useUpdateCountry(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: CountryUpdateInput) => updateCountry(id, input),
    onSuccess: (updated) => {
      client.setQueryData(countryKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: countryKeys.lists() });
    },
  });
}

export function useChangeCountryStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeCountryStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: countryKeys.all }),
  });
}

export function useDeleteCountry() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteCountry(id),
    onSuccess: () => client.invalidateQueries({ queryKey: countryKeys.all }),
  });
}

export function useBulkCreateCountrys() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: CountryInput[]) => bulkCreateCountrys(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: countryKeys.all }),
  });
}

export function useExportCountrys() {
  return useMutation({
    mutationFn: (filter: CountryFilter) => exportCountrysCsv(filter),
  });
}
