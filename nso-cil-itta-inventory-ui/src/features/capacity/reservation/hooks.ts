import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateReservations,
  changeReservationStatus,
  createReservation,
  deleteReservation,
  exportReservationsCsv,
  getReservation,
  getReservationStatusStats,
  listReservationSummaries,
  searchReservations,
  updateReservation,

} from './api';
import type { ReservationFilter, ReservationInput, ReservationUpdateInput } from './types';

export const reservationKeys = {
  all: ['reservation'] as const,
  lists: () => [...reservationKeys.all, 'list'] as const,
  list: (filter: ReservationFilter, page: number, size: number, sort?: string) =>
    [...reservationKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...reservationKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...reservationKeys.all, 'summaries', status] as const,
  stats: () => [...reservationKeys.all, 'stats'] as const,
};

export function useReservationList(filter: ReservationFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: reservationKeys.list(filter, page, size, sort),
    queryFn: () => searchReservations(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useReservation(id: string | undefined) {
  return useQuery({
    queryKey: reservationKeys.detail(id ?? ''),
    queryFn: () => getReservation(id as string),
    enabled: Boolean(id),
  });
}

export function useReservationSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: reservationKeys.summaries(status),
    queryFn: () => listReservationSummaries(status),
    staleTime: 60_000,
  });
}

export function useReservationStatusStats() {
  return useQuery({
    queryKey: reservationKeys.stats(),
    queryFn: getReservationStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateReservation() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ReservationInput) => createReservation(input),
    onSuccess: () => client.invalidateQueries({ queryKey: reservationKeys.all }),
  });
}

export function useUpdateReservation(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ReservationUpdateInput) => updateReservation(id, input),
    onSuccess: (updated) => {
      client.setQueryData(reservationKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: reservationKeys.lists() });
    },
  });
}

export function useChangeReservationStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeReservationStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: reservationKeys.all }),
  });
}

export function useDeleteReservation() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteReservation(id),
    onSuccess: () => client.invalidateQueries({ queryKey: reservationKeys.all }),
  });
}

export function useBulkCreateReservations() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: ReservationInput[]) => bulkCreateReservations(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: reservationKeys.all }),
  });
}

export function useExportReservations() {
  return useMutation({
    mutationFn: (filter: ReservationFilter) => exportReservationsCsv(filter),
  });
}
