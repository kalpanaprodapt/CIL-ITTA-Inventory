import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateDnsRecords,
  changeDnsRecordStatus,
  createDnsRecord,
  deleteDnsRecord,
  exportDnsRecordsCsv,
  getDnsRecord,
  getDnsRecordStatusStats,
  listDnsRecordSummaries,
  searchDnsRecords,
  updateDnsRecord,

} from './api';
import type { DnsRecordFilter, DnsRecordInput, DnsRecordUpdateInput } from './types';

export const dnsRecordKeys = {
  all: ['dnsRecord'] as const,
  lists: () => [...dnsRecordKeys.all, 'list'] as const,
  list: (filter: DnsRecordFilter, page: number, size: number, sort?: string) =>
    [...dnsRecordKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...dnsRecordKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...dnsRecordKeys.all, 'summaries', status] as const,
  stats: () => [...dnsRecordKeys.all, 'stats'] as const,
};

export function useDnsRecordList(filter: DnsRecordFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: dnsRecordKeys.list(filter, page, size, sort),
    queryFn: () => searchDnsRecords(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useDnsRecord(id: string | undefined) {
  return useQuery({
    queryKey: dnsRecordKeys.detail(id ?? ''),
    queryFn: () => getDnsRecord(id as string),
    enabled: Boolean(id),
  });
}

export function useDnsRecordSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: dnsRecordKeys.summaries(status),
    queryFn: () => listDnsRecordSummaries(status),
    staleTime: 60_000,
  });
}

export function useDnsRecordStatusStats() {
  return useQuery({
    queryKey: dnsRecordKeys.stats(),
    queryFn: getDnsRecordStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateDnsRecord() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: DnsRecordInput) => createDnsRecord(input),
    onSuccess: () => client.invalidateQueries({ queryKey: dnsRecordKeys.all }),
  });
}

export function useUpdateDnsRecord(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: DnsRecordUpdateInput) => updateDnsRecord(id, input),
    onSuccess: (updated) => {
      client.setQueryData(dnsRecordKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: dnsRecordKeys.lists() });
    },
  });
}

export function useChangeDnsRecordStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeDnsRecordStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: dnsRecordKeys.all }),
  });
}

export function useDeleteDnsRecord() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteDnsRecord(id),
    onSuccess: () => client.invalidateQueries({ queryKey: dnsRecordKeys.all }),
  });
}

export function useBulkCreateDnsRecords() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: DnsRecordInput[]) => bulkCreateDnsRecords(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: dnsRecordKeys.all }),
  });
}

export function useExportDnsRecords() {
  return useMutation({
    mutationFn: (filter: DnsRecordFilter) => exportDnsRecordsCsv(filter),
  });
}
