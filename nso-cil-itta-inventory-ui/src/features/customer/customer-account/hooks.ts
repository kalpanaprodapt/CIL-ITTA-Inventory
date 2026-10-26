import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateCustomerAccounts,
  changeCustomerAccountStatus,
  createCustomerAccount,
  deleteCustomerAccount,
  exportCustomerAccountsCsv,
  getCustomerAccount,
  getCustomerAccountStatusStats,
  listCustomerAccountSummaries,
  searchCustomerAccounts,
  updateCustomerAccount,
  listCustomerAccountByCustomerId,
} from './api';
import type { CustomerAccountFilter, CustomerAccountInput, CustomerAccountUpdateInput } from './types';

export const customerAccountKeys = {
  all: ['customerAccount'] as const,
  lists: () => [...customerAccountKeys.all, 'list'] as const,
  list: (filter: CustomerAccountFilter, page: number, size: number, sort?: string) =>
    [...customerAccountKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...customerAccountKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...customerAccountKeys.all, 'summaries', status] as const,
  stats: () => [...customerAccountKeys.all, 'stats'] as const,
};

export function useCustomerAccountList(filter: CustomerAccountFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: customerAccountKeys.list(filter, page, size, sort),
    queryFn: () => searchCustomerAccounts(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useCustomerAccount(id: string | undefined) {
  return useQuery({
    queryKey: customerAccountKeys.detail(id ?? ''),
    queryFn: () => getCustomerAccount(id as string),
    enabled: Boolean(id),
  });
}

export function useCustomerAccountSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: customerAccountKeys.summaries(status),
    queryFn: () => listCustomerAccountSummaries(status),
    staleTime: 60_000,
  });
}

export function useCustomerAccountStatusStats() {
  return useQuery({
    queryKey: customerAccountKeys.stats(),
    queryFn: getCustomerAccountStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateCustomerAccount() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: CustomerAccountInput) => createCustomerAccount(input),
    onSuccess: () => client.invalidateQueries({ queryKey: customerAccountKeys.all }),
  });
}

export function useUpdateCustomerAccount(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: CustomerAccountUpdateInput) => updateCustomerAccount(id, input),
    onSuccess: (updated) => {
      client.setQueryData(customerAccountKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: customerAccountKeys.lists() });
    },
  });
}

export function useChangeCustomerAccountStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeCustomerAccountStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: customerAccountKeys.all }),
  });
}

export function useDeleteCustomerAccount() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteCustomerAccount(id),
    onSuccess: () => client.invalidateQueries({ queryKey: customerAccountKeys.all }),
  });
}

export function useBulkCreateCustomerAccounts() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: CustomerAccountInput[]) => bulkCreateCustomerAccounts(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: customerAccountKeys.all }),
  });
}

export function useExportCustomerAccounts() {
  return useMutation({
    mutationFn: (filter: CustomerAccountFilter) => exportCustomerAccountsCsv(filter),
  });
}

export function useCustomerAccountsByCustomerId(customerId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...customerAccountKeys.all, 'by-customer_id', customerId, page, size],
    queryFn: () => listCustomerAccountByCustomerId(customerId as string, page, size),
    enabled: Boolean(customerId),
  });
}
