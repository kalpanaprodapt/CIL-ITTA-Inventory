import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateAuditRecords,
  changeAuditRecordStatus,
  createAuditRecord,
  deleteAuditRecord,
  exportAuditRecordsCsv,
  getAuditRecord,
  getAuditRecordStatusStats,
  listAuditRecordSummaries,
  searchAuditRecords,
  updateAuditRecord,

} from './api';
import type { AuditRecordFilter, AuditRecordInput, AuditRecordUpdateInput } from './types';

export const auditRecordKeys = {
  all: ['auditRecord'] as const,
  lists: () => [...auditRecordKeys.all, 'list'] as const,
  list: (filter: AuditRecordFilter, page: number, size: number, sort?: string) =>
    [...auditRecordKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...auditRecordKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...auditRecordKeys.all, 'summaries', status] as const,
  stats: () => [...auditRecordKeys.all, 'stats'] as const,
};

export function useAuditRecordList(filter: AuditRecordFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: auditRecordKeys.list(filter, page, size, sort),
    queryFn: () => searchAuditRecords(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useAuditRecord(id: string | undefined) {
  return useQuery({
    queryKey: auditRecordKeys.detail(id ?? ''),
    queryFn: () => getAuditRecord(id as string),
    enabled: Boolean(id),
  });
}

export function useAuditRecordSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: auditRecordKeys.summaries(status),
    queryFn: () => listAuditRecordSummaries(status),
    staleTime: 60_000,
  });
}

export function useAuditRecordStatusStats() {
  return useQuery({
    queryKey: auditRecordKeys.stats(),
    queryFn: getAuditRecordStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateAuditRecord() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: AuditRecordInput) => createAuditRecord(input),
    onSuccess: () => client.invalidateQueries({ queryKey: auditRecordKeys.all }),
  });
}

export function useUpdateAuditRecord(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: AuditRecordUpdateInput) => updateAuditRecord(id, input),
    onSuccess: (updated) => {
      client.setQueryData(auditRecordKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: auditRecordKeys.lists() });
    },
  });
}

export function useChangeAuditRecordStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeAuditRecordStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: auditRecordKeys.all }),
  });
}

export function useDeleteAuditRecord() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteAuditRecord(id),
    onSuccess: () => client.invalidateQueries({ queryKey: auditRecordKeys.all }),
  });
}

export function useBulkCreateAuditRecords() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: AuditRecordInput[]) => bulkCreateAuditRecords(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: auditRecordKeys.all }),
  });
}

export function useExportAuditRecords() {
  return useMutation({
    mutationFn: (filter: AuditRecordFilter) => exportAuditRecordsCsv(filter),
  });
}
