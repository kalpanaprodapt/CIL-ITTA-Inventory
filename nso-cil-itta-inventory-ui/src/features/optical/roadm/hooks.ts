import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateRoadms,
  changeRoadmStatus,
  createRoadm,
  deleteRoadm,
  exportRoadmsCsv,
  getRoadm,
  getRoadmStatusStats,
  listRoadmSummaries,
  searchRoadms,
  updateRoadm,
  listRoadmBySystemId,
} from './api';
import type { RoadmFilter, RoadmInput, RoadmUpdateInput } from './types';

export const roadmKeys = {
  all: ['roadm'] as const,
  lists: () => [...roadmKeys.all, 'list'] as const,
  list: (filter: RoadmFilter, page: number, size: number, sort?: string) =>
    [...roadmKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...roadmKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...roadmKeys.all, 'summaries', status] as const,
  stats: () => [...roadmKeys.all, 'stats'] as const,
};

export function useRoadmList(filter: RoadmFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: roadmKeys.list(filter, page, size, sort),
    queryFn: () => searchRoadms(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useRoadm(id: string | undefined) {
  return useQuery({
    queryKey: roadmKeys.detail(id ?? ''),
    queryFn: () => getRoadm(id as string),
    enabled: Boolean(id),
  });
}

export function useRoadmSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: roadmKeys.summaries(status),
    queryFn: () => listRoadmSummaries(status),
    staleTime: 60_000,
  });
}

export function useRoadmStatusStats() {
  return useQuery({
    queryKey: roadmKeys.stats(),
    queryFn: getRoadmStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateRoadm() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: RoadmInput) => createRoadm(input),
    onSuccess: () => client.invalidateQueries({ queryKey: roadmKeys.all }),
  });
}

export function useUpdateRoadm(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: RoadmUpdateInput) => updateRoadm(id, input),
    onSuccess: (updated) => {
      client.setQueryData(roadmKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: roadmKeys.lists() });
    },
  });
}

export function useChangeRoadmStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeRoadmStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: roadmKeys.all }),
  });
}

export function useDeleteRoadm() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteRoadm(id),
    onSuccess: () => client.invalidateQueries({ queryKey: roadmKeys.all }),
  });
}

export function useBulkCreateRoadms() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: RoadmInput[]) => bulkCreateRoadms(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: roadmKeys.all }),
  });
}

export function useExportRoadms() {
  return useMutation({
    mutationFn: (filter: RoadmFilter) => exportRoadmsCsv(filter),
  });
}

export function useRoadmsBySystemId(systemId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...roadmKeys.all, 'by-system_id', systemId, page, size],
    queryFn: () => listRoadmBySystemId(systemId as string, page, size),
    enabled: Boolean(systemId),
  });
}
