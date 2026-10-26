import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateComplianceExceptions,
  changeComplianceExceptionStatus,
  createComplianceException,
  deleteComplianceException,
  exportComplianceExceptionsCsv,
  getComplianceException,
  getComplianceExceptionStatusStats,
  listComplianceExceptionSummaries,
  searchComplianceExceptions,
  updateComplianceException,
  listComplianceExceptionByRuleId,
} from './api';
import type { ComplianceExceptionFilter, ComplianceExceptionInput, ComplianceExceptionUpdateInput } from './types';

export const complianceExceptionKeys = {
  all: ['complianceException'] as const,
  lists: () => [...complianceExceptionKeys.all, 'list'] as const,
  list: (filter: ComplianceExceptionFilter, page: number, size: number, sort?: string) =>
    [...complianceExceptionKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...complianceExceptionKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...complianceExceptionKeys.all, 'summaries', status] as const,
  stats: () => [...complianceExceptionKeys.all, 'stats'] as const,
};

export function useComplianceExceptionList(filter: ComplianceExceptionFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: complianceExceptionKeys.list(filter, page, size, sort),
    queryFn: () => searchComplianceExceptions(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useComplianceException(id: string | undefined) {
  return useQuery({
    queryKey: complianceExceptionKeys.detail(id ?? ''),
    queryFn: () => getComplianceException(id as string),
    enabled: Boolean(id),
  });
}

export function useComplianceExceptionSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: complianceExceptionKeys.summaries(status),
    queryFn: () => listComplianceExceptionSummaries(status),
    staleTime: 60_000,
  });
}

export function useComplianceExceptionStatusStats() {
  return useQuery({
    queryKey: complianceExceptionKeys.stats(),
    queryFn: getComplianceExceptionStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateComplianceException() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ComplianceExceptionInput) => createComplianceException(input),
    onSuccess: () => client.invalidateQueries({ queryKey: complianceExceptionKeys.all }),
  });
}

export function useUpdateComplianceException(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ComplianceExceptionUpdateInput) => updateComplianceException(id, input),
    onSuccess: (updated) => {
      client.setQueryData(complianceExceptionKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: complianceExceptionKeys.lists() });
    },
  });
}

export function useChangeComplianceExceptionStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeComplianceExceptionStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: complianceExceptionKeys.all }),
  });
}

export function useDeleteComplianceException() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteComplianceException(id),
    onSuccess: () => client.invalidateQueries({ queryKey: complianceExceptionKeys.all }),
  });
}

export function useBulkCreateComplianceExceptions() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: ComplianceExceptionInput[]) => bulkCreateComplianceExceptions(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: complianceExceptionKeys.all }),
  });
}

export function useExportComplianceExceptions() {
  return useMutation({
    mutationFn: (filter: ComplianceExceptionFilter) => exportComplianceExceptionsCsv(filter),
  });
}

export function useComplianceExceptionsByRuleId(ruleId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...complianceExceptionKeys.all, 'by-rule_id', ruleId, page, size],
    queryFn: () => listComplianceExceptionByRuleId(ruleId as string, page, size),
    enabled: Boolean(ruleId),
  });
}
