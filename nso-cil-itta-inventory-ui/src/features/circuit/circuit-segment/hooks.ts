import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateCircuitSegments,
  changeCircuitSegmentStatus,
  createCircuitSegment,
  deleteCircuitSegment,
  exportCircuitSegmentsCsv,
  getCircuitSegment,
  getCircuitSegmentStatusStats,
  listCircuitSegmentSummaries,
  searchCircuitSegments,
  updateCircuitSegment,
  listCircuitSegmentByCircuitId,
  listCircuitSegmentByATerminationId,
  listCircuitSegmentByZTerminationId,
} from './api';
import type { CircuitSegmentFilter, CircuitSegmentInput, CircuitSegmentUpdateInput } from './types';

export const circuitSegmentKeys = {
  all: ['circuitSegment'] as const,
  lists: () => [...circuitSegmentKeys.all, 'list'] as const,
  list: (filter: CircuitSegmentFilter, page: number, size: number, sort?: string) =>
    [...circuitSegmentKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...circuitSegmentKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...circuitSegmentKeys.all, 'summaries', status] as const,
  stats: () => [...circuitSegmentKeys.all, 'stats'] as const,
};

export function useCircuitSegmentList(filter: CircuitSegmentFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: circuitSegmentKeys.list(filter, page, size, sort),
    queryFn: () => searchCircuitSegments(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useCircuitSegment(id: string | undefined) {
  return useQuery({
    queryKey: circuitSegmentKeys.detail(id ?? ''),
    queryFn: () => getCircuitSegment(id as string),
    enabled: Boolean(id),
  });
}

export function useCircuitSegmentSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: circuitSegmentKeys.summaries(status),
    queryFn: () => listCircuitSegmentSummaries(status),
    staleTime: 60_000,
  });
}

export function useCircuitSegmentStatusStats() {
  return useQuery({
    queryKey: circuitSegmentKeys.stats(),
    queryFn: getCircuitSegmentStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateCircuitSegment() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: CircuitSegmentInput) => createCircuitSegment(input),
    onSuccess: () => client.invalidateQueries({ queryKey: circuitSegmentKeys.all }),
  });
}

export function useUpdateCircuitSegment(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: CircuitSegmentUpdateInput) => updateCircuitSegment(id, input),
    onSuccess: (updated) => {
      client.setQueryData(circuitSegmentKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: circuitSegmentKeys.lists() });
    },
  });
}

export function useChangeCircuitSegmentStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeCircuitSegmentStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: circuitSegmentKeys.all }),
  });
}

export function useDeleteCircuitSegment() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteCircuitSegment(id),
    onSuccess: () => client.invalidateQueries({ queryKey: circuitSegmentKeys.all }),
  });
}

export function useBulkCreateCircuitSegments() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: CircuitSegmentInput[]) => bulkCreateCircuitSegments(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: circuitSegmentKeys.all }),
  });
}

export function useExportCircuitSegments() {
  return useMutation({
    mutationFn: (filter: CircuitSegmentFilter) => exportCircuitSegmentsCsv(filter),
  });
}

export function useCircuitSegmentsByCircuitId(circuitId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...circuitSegmentKeys.all, 'by-circuit_id', circuitId, page, size],
    queryFn: () => listCircuitSegmentByCircuitId(circuitId as string, page, size),
    enabled: Boolean(circuitId),
  });
}

export function useCircuitSegmentsByATerminationId(aTerminationId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...circuitSegmentKeys.all, 'by-a_termination_id', aTerminationId, page, size],
    queryFn: () => listCircuitSegmentByATerminationId(aTerminationId as string, page, size),
    enabled: Boolean(aTerminationId),
  });
}

export function useCircuitSegmentsByZTerminationId(zTerminationId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...circuitSegmentKeys.all, 'by-z_termination_id', zTerminationId, page, size],
    queryFn: () => listCircuitSegmentByZTerminationId(zTerminationId as string, page, size),
    enabled: Boolean(zTerminationId),
  });
}
