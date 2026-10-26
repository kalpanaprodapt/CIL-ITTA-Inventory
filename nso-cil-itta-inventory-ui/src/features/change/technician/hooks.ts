import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateTechnicians,
  changeTechnicianStatus,
  createTechnician,
  deleteTechnician,
  exportTechniciansCsv,
  getTechnician,
  getTechnicianStatusStats,
  listTechnicianSummaries,
  searchTechnicians,
  updateTechnician,
  listTechnicianByHomeSiteId,
} from './api';
import type { TechnicianFilter, TechnicianInput, TechnicianUpdateInput } from './types';

export const technicianKeys = {
  all: ['technician'] as const,
  lists: () => [...technicianKeys.all, 'list'] as const,
  list: (filter: TechnicianFilter, page: number, size: number, sort?: string) =>
    [...technicianKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...technicianKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...technicianKeys.all, 'summaries', status] as const,
  stats: () => [...technicianKeys.all, 'stats'] as const,
};

export function useTechnicianList(filter: TechnicianFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: technicianKeys.list(filter, page, size, sort),
    queryFn: () => searchTechnicians(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useTechnician(id: string | undefined) {
  return useQuery({
    queryKey: technicianKeys.detail(id ?? ''),
    queryFn: () => getTechnician(id as string),
    enabled: Boolean(id),
  });
}

export function useTechnicianSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: technicianKeys.summaries(status),
    queryFn: () => listTechnicianSummaries(status),
    staleTime: 60_000,
  });
}

export function useTechnicianStatusStats() {
  return useQuery({
    queryKey: technicianKeys.stats(),
    queryFn: getTechnicianStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateTechnician() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: TechnicianInput) => createTechnician(input),
    onSuccess: () => client.invalidateQueries({ queryKey: technicianKeys.all }),
  });
}

export function useUpdateTechnician(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: TechnicianUpdateInput) => updateTechnician(id, input),
    onSuccess: (updated) => {
      client.setQueryData(technicianKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: technicianKeys.lists() });
    },
  });
}

export function useChangeTechnicianStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeTechnicianStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: technicianKeys.all }),
  });
}

export function useDeleteTechnician() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteTechnician(id),
    onSuccess: () => client.invalidateQueries({ queryKey: technicianKeys.all }),
  });
}

export function useBulkCreateTechnicians() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: TechnicianInput[]) => bulkCreateTechnicians(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: technicianKeys.all }),
  });
}

export function useExportTechnicians() {
  return useMutation({
    mutationFn: (filter: TechnicianFilter) => exportTechniciansCsv(filter),
  });
}

export function useTechniciansByHomeSiteId(homeSiteId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...technicianKeys.all, 'by-home_site_id', homeSiteId, page, size],
    queryFn: () => listTechnicianByHomeSiteId(homeSiteId as string, page, size),
    enabled: Boolean(homeSiteId),
  });
}
