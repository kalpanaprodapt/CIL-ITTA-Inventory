import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateTicketLinks,
  changeTicketLinkStatus,
  createTicketLink,
  deleteTicketLink,
  exportTicketLinksCsv,
  getTicketLink,
  getTicketLinkStatusStats,
  listTicketLinkSummaries,
  searchTicketLinks,
  updateTicketLink,
  listTicketLinkByAlarmId,
} from './api';
import type { TicketLinkFilter, TicketLinkInput, TicketLinkUpdateInput } from './types';

export const ticketLinkKeys = {
  all: ['ticketLink'] as const,
  lists: () => [...ticketLinkKeys.all, 'list'] as const,
  list: (filter: TicketLinkFilter, page: number, size: number, sort?: string) =>
    [...ticketLinkKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...ticketLinkKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...ticketLinkKeys.all, 'summaries', status] as const,
  stats: () => [...ticketLinkKeys.all, 'stats'] as const,
};

export function useTicketLinkList(filter: TicketLinkFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: ticketLinkKeys.list(filter, page, size, sort),
    queryFn: () => searchTicketLinks(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useTicketLink(id: string | undefined) {
  return useQuery({
    queryKey: ticketLinkKeys.detail(id ?? ''),
    queryFn: () => getTicketLink(id as string),
    enabled: Boolean(id),
  });
}

export function useTicketLinkSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: ticketLinkKeys.summaries(status),
    queryFn: () => listTicketLinkSummaries(status),
    staleTime: 60_000,
  });
}

export function useTicketLinkStatusStats() {
  return useQuery({
    queryKey: ticketLinkKeys.stats(),
    queryFn: getTicketLinkStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateTicketLink() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: TicketLinkInput) => createTicketLink(input),
    onSuccess: () => client.invalidateQueries({ queryKey: ticketLinkKeys.all }),
  });
}

export function useUpdateTicketLink(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: TicketLinkUpdateInput) => updateTicketLink(id, input),
    onSuccess: (updated) => {
      client.setQueryData(ticketLinkKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: ticketLinkKeys.lists() });
    },
  });
}

export function useChangeTicketLinkStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeTicketLinkStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: ticketLinkKeys.all }),
  });
}

export function useDeleteTicketLink() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteTicketLink(id),
    onSuccess: () => client.invalidateQueries({ queryKey: ticketLinkKeys.all }),
  });
}

export function useBulkCreateTicketLinks() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: TicketLinkInput[]) => bulkCreateTicketLinks(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: ticketLinkKeys.all }),
  });
}

export function useExportTicketLinks() {
  return useMutation({
    mutationFn: (filter: TicketLinkFilter) => exportTicketLinksCsv(filter),
  });
}

export function useTicketLinksByAlarmId(alarmId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...ticketLinkKeys.all, 'by-alarm_id', alarmId, page, size],
    queryFn: () => listTicketLinkByAlarmId(alarmId as string, page, size),
    enabled: Boolean(alarmId),
  });
}
