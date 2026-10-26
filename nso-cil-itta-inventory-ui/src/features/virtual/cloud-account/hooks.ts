import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateCloudAccounts,
  changeCloudAccountStatus,
  createCloudAccount,
  deleteCloudAccount,
  exportCloudAccountsCsv,
  getCloudAccount,
  getCloudAccountStatusStats,
  listCloudAccountSummaries,
  searchCloudAccounts,
  updateCloudAccount,

} from './api';
import type { CloudAccountFilter, CloudAccountInput, CloudAccountUpdateInput } from './types';

export const cloudAccountKeys = {
  all: ['cloudAccount'] as const,
  lists: () => [...cloudAccountKeys.all, 'list'] as const,
  list: (filter: CloudAccountFilter, page: number, size: number, sort?: string) =>
    [...cloudAccountKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...cloudAccountKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...cloudAccountKeys.all, 'summaries', status] as const,
  stats: () => [...cloudAccountKeys.all, 'stats'] as const,
};

export function useCloudAccountList(filter: CloudAccountFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: cloudAccountKeys.list(filter, page, size, sort),
    queryFn: () => searchCloudAccounts(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useCloudAccount(id: string | undefined) {
  return useQuery({
    queryKey: cloudAccountKeys.detail(id ?? ''),
    queryFn: () => getCloudAccount(id as string),
    enabled: Boolean(id),
  });
}

export function useCloudAccountSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: cloudAccountKeys.summaries(status),
    queryFn: () => listCloudAccountSummaries(status),
    staleTime: 60_000,
  });
}

export function useCloudAccountStatusStats() {
  return useQuery({
    queryKey: cloudAccountKeys.stats(),
    queryFn: getCloudAccountStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateCloudAccount() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: CloudAccountInput) => createCloudAccount(input),
    onSuccess: () => client.invalidateQueries({ queryKey: cloudAccountKeys.all }),
  });
}

export function useUpdateCloudAccount(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: CloudAccountUpdateInput) => updateCloudAccount(id, input),
    onSuccess: (updated) => {
      client.setQueryData(cloudAccountKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: cloudAccountKeys.lists() });
    },
  });
}

export function useChangeCloudAccountStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeCloudAccountStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: cloudAccountKeys.all }),
  });
}

export function useDeleteCloudAccount() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteCloudAccount(id),
    onSuccess: () => client.invalidateQueries({ queryKey: cloudAccountKeys.all }),
  });
}

export function useBulkCreateCloudAccounts() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: CloudAccountInput[]) => bulkCreateCloudAccounts(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: cloudAccountKeys.all }),
  });
}

export function useExportCloudAccounts() {
  return useMutation({
    mutationFn: (filter: CloudAccountFilter) => exportCloudAccountsCsv(filter),
  });
}
