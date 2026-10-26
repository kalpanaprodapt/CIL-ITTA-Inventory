import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateLineCards,
  changeLineCardStatus,
  createLineCard,
  deleteLineCard,
  exportLineCardsCsv,
  getLineCard,
  getLineCardStatusStats,
  listLineCardSummaries,
  searchLineCards,
  updateLineCard,
  listLineCardByChassisId,
} from './api';
import type { LineCardFilter, LineCardInput, LineCardUpdateInput } from './types';

export const lineCardKeys = {
  all: ['lineCard'] as const,
  lists: () => [...lineCardKeys.all, 'list'] as const,
  list: (filter: LineCardFilter, page: number, size: number, sort?: string) =>
    [...lineCardKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...lineCardKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...lineCardKeys.all, 'summaries', status] as const,
  stats: () => [...lineCardKeys.all, 'stats'] as const,
};

export function useLineCardList(filter: LineCardFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: lineCardKeys.list(filter, page, size, sort),
    queryFn: () => searchLineCards(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useLineCard(id: string | undefined) {
  return useQuery({
    queryKey: lineCardKeys.detail(id ?? ''),
    queryFn: () => getLineCard(id as string),
    enabled: Boolean(id),
  });
}

export function useLineCardSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: lineCardKeys.summaries(status),
    queryFn: () => listLineCardSummaries(status),
    staleTime: 60_000,
  });
}

export function useLineCardStatusStats() {
  return useQuery({
    queryKey: lineCardKeys.stats(),
    queryFn: getLineCardStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateLineCard() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: LineCardInput) => createLineCard(input),
    onSuccess: () => client.invalidateQueries({ queryKey: lineCardKeys.all }),
  });
}

export function useUpdateLineCard(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: LineCardUpdateInput) => updateLineCard(id, input),
    onSuccess: (updated) => {
      client.setQueryData(lineCardKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: lineCardKeys.lists() });
    },
  });
}

export function useChangeLineCardStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeLineCardStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: lineCardKeys.all }),
  });
}

export function useDeleteLineCard() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteLineCard(id),
    onSuccess: () => client.invalidateQueries({ queryKey: lineCardKeys.all }),
  });
}

export function useBulkCreateLineCards() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: LineCardInput[]) => bulkCreateLineCards(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: lineCardKeys.all }),
  });
}

export function useExportLineCards() {
  return useMutation({
    mutationFn: (filter: LineCardFilter) => exportLineCardsCsv(filter),
  });
}

export function useLineCardsByChassisId(chassisId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...lineCardKeys.all, 'by-chassis_id', chassisId, page, size],
    queryFn: () => listLineCardByChassisId(chassisId as string, page, size),
    enabled: Boolean(chassisId),
  });
}
