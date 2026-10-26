import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreatePolicyDocuments,
  changePolicyDocumentStatus,
  createPolicyDocument,
  deletePolicyDocument,
  exportPolicyDocumentsCsv,
  getPolicyDocument,
  getPolicyDocumentStatusStats,
  listPolicyDocumentSummaries,
  searchPolicyDocuments,
  updatePolicyDocument,

} from './api';
import type { PolicyDocumentFilter, PolicyDocumentInput, PolicyDocumentUpdateInput } from './types';

export const policyDocumentKeys = {
  all: ['policyDocument'] as const,
  lists: () => [...policyDocumentKeys.all, 'list'] as const,
  list: (filter: PolicyDocumentFilter, page: number, size: number, sort?: string) =>
    [...policyDocumentKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...policyDocumentKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...policyDocumentKeys.all, 'summaries', status] as const,
  stats: () => [...policyDocumentKeys.all, 'stats'] as const,
};

export function usePolicyDocumentList(filter: PolicyDocumentFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: policyDocumentKeys.list(filter, page, size, sort),
    queryFn: () => searchPolicyDocuments(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function usePolicyDocument(id: string | undefined) {
  return useQuery({
    queryKey: policyDocumentKeys.detail(id ?? ''),
    queryFn: () => getPolicyDocument(id as string),
    enabled: Boolean(id),
  });
}

export function usePolicyDocumentSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: policyDocumentKeys.summaries(status),
    queryFn: () => listPolicyDocumentSummaries(status),
    staleTime: 60_000,
  });
}

export function usePolicyDocumentStatusStats() {
  return useQuery({
    queryKey: policyDocumentKeys.stats(),
    queryFn: getPolicyDocumentStatusStats,
    staleTime: 30_000,
  });
}

export function useCreatePolicyDocument() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: PolicyDocumentInput) => createPolicyDocument(input),
    onSuccess: () => client.invalidateQueries({ queryKey: policyDocumentKeys.all }),
  });
}

export function useUpdatePolicyDocument(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: PolicyDocumentUpdateInput) => updatePolicyDocument(id, input),
    onSuccess: (updated) => {
      client.setQueryData(policyDocumentKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: policyDocumentKeys.lists() });
    },
  });
}

export function useChangePolicyDocumentStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changePolicyDocumentStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: policyDocumentKeys.all }),
  });
}

export function useDeletePolicyDocument() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deletePolicyDocument(id),
    onSuccess: () => client.invalidateQueries({ queryKey: policyDocumentKeys.all }),
  });
}

export function useBulkCreatePolicyDocuments() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: PolicyDocumentInput[]) => bulkCreatePolicyDocuments(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: policyDocumentKeys.all }),
  });
}

export function useExportPolicyDocuments() {
  return useMutation({
    mutationFn: (filter: PolicyDocumentFilter) => exportPolicyDocumentsCsv(filter),
  });
}
