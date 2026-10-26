import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateCustomers,
  changeCustomerStatus,
  createCustomer,
  deleteCustomer,
  exportCustomersCsv,
  getCustomer,
  getCustomerStatusStats,
  listCustomerSummaries,
  searchCustomers,
  updateCustomer,

} from './api';
import type { CustomerFilter, CustomerInput, CustomerUpdateInput } from './types';

export const customerKeys = {
  all: ['customer'] as const,
  lists: () => [...customerKeys.all, 'list'] as const,
  list: (filter: CustomerFilter, page: number, size: number, sort?: string) =>
    [...customerKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...customerKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...customerKeys.all, 'summaries', status] as const,
  stats: () => [...customerKeys.all, 'stats'] as const,
};

export function useCustomerList(filter: CustomerFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: customerKeys.list(filter, page, size, sort),
    queryFn: () => searchCustomers(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useCustomer(id: string | undefined) {
  return useQuery({
    queryKey: customerKeys.detail(id ?? ''),
    queryFn: () => getCustomer(id as string),
    enabled: Boolean(id),
  });
}

export function useCustomerSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: customerKeys.summaries(status),
    queryFn: () => listCustomerSummaries(status),
    staleTime: 60_000,
  });
}

export function useCustomerStatusStats() {
  return useQuery({
    queryKey: customerKeys.stats(),
    queryFn: getCustomerStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateCustomer() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: CustomerInput) => createCustomer(input),
    onSuccess: () => client.invalidateQueries({ queryKey: customerKeys.all }),
  });
}

export function useUpdateCustomer(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: CustomerUpdateInput) => updateCustomer(id, input),
    onSuccess: (updated) => {
      client.setQueryData(customerKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: customerKeys.lists() });
    },
  });
}

export function useChangeCustomerStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeCustomerStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: customerKeys.all }),
  });
}

export function useDeleteCustomer() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteCustomer(id),
    onSuccess: () => client.invalidateQueries({ queryKey: customerKeys.all }),
  });
}

export function useBulkCreateCustomers() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: CustomerInput[]) => bulkCreateCustomers(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: customerKeys.all }),
  });
}

export function useExportCustomers() {
  return useMutation({
    mutationFn: (filter: CustomerFilter) => exportCustomersCsv(filter),
  });
}
