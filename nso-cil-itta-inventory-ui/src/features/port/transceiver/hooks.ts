import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateTransceivers,
  changeTransceiverStatus,
  createTransceiver,
  deleteTransceiver,
  exportTransceiversCsv,
  getTransceiver,
  getTransceiverStatusStats,
  listTransceiverSummaries,
  searchTransceivers,
  updateTransceiver,
  listTransceiverByPortId,
} from './api';
import type { TransceiverFilter, TransceiverInput, TransceiverUpdateInput } from './types';

export const transceiverKeys = {
  all: ['transceiver'] as const,
  lists: () => [...transceiverKeys.all, 'list'] as const,
  list: (filter: TransceiverFilter, page: number, size: number, sort?: string) =>
    [...transceiverKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...transceiverKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...transceiverKeys.all, 'summaries', status] as const,
  stats: () => [...transceiverKeys.all, 'stats'] as const,
};

export function useTransceiverList(filter: TransceiverFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: transceiverKeys.list(filter, page, size, sort),
    queryFn: () => searchTransceivers(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useTransceiver(id: string | undefined) {
  return useQuery({
    queryKey: transceiverKeys.detail(id ?? ''),
    queryFn: () => getTransceiver(id as string),
    enabled: Boolean(id),
  });
}

export function useTransceiverSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: transceiverKeys.summaries(status),
    queryFn: () => listTransceiverSummaries(status),
    staleTime: 60_000,
  });
}

export function useTransceiverStatusStats() {
  return useQuery({
    queryKey: transceiverKeys.stats(),
    queryFn: getTransceiverStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateTransceiver() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: TransceiverInput) => createTransceiver(input),
    onSuccess: () => client.invalidateQueries({ queryKey: transceiverKeys.all }),
  });
}

export function useUpdateTransceiver(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: TransceiverUpdateInput) => updateTransceiver(id, input),
    onSuccess: (updated) => {
      client.setQueryData(transceiverKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: transceiverKeys.lists() });
    },
  });
}

export function useChangeTransceiverStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeTransceiverStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: transceiverKeys.all }),
  });
}

export function useDeleteTransceiver() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteTransceiver(id),
    onSuccess: () => client.invalidateQueries({ queryKey: transceiverKeys.all }),
  });
}

export function useBulkCreateTransceivers() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: TransceiverInput[]) => bulkCreateTransceivers(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: transceiverKeys.all }),
  });
}

export function useExportTransceivers() {
  return useMutation({
    mutationFn: (filter: TransceiverFilter) => exportTransceiversCsv(filter),
  });
}

export function useTransceiversByPortId(portId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...transceiverKeys.all, 'by-port_id', portId, page, size],
    queryFn: () => listTransceiverByPortId(portId as string, page, size),
    enabled: Boolean(portId),
  });
}
