import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateCustomerContacts,
  changeCustomerContactStatus,
  createCustomerContact,
  deleteCustomerContact,
  exportCustomerContactsCsv,
  getCustomerContact,
  getCustomerContactStatusStats,
  listCustomerContactSummaries,
  searchCustomerContacts,
  updateCustomerContact,
  listCustomerContactByCustomerId,
} from './api';
import type { CustomerContactFilter, CustomerContactInput, CustomerContactUpdateInput } from './types';

export const customerContactKeys = {
  all: ['customerContact'] as const,
  lists: () => [...customerContactKeys.all, 'list'] as const,
  list: (filter: CustomerContactFilter, page: number, size: number, sort?: string) =>
    [...customerContactKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...customerContactKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...customerContactKeys.all, 'summaries', status] as const,
  stats: () => [...customerContactKeys.all, 'stats'] as const,
};

export function useCustomerContactList(filter: CustomerContactFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: customerContactKeys.list(filter, page, size, sort),
    queryFn: () => searchCustomerContacts(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useCustomerContact(id: string | undefined) {
  return useQuery({
    queryKey: customerContactKeys.detail(id ?? ''),
    queryFn: () => getCustomerContact(id as string),
    enabled: Boolean(id),
  });
}

export function useCustomerContactSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: customerContactKeys.summaries(status),
    queryFn: () => listCustomerContactSummaries(status),
    staleTime: 60_000,
  });
}

export function useCustomerContactStatusStats() {
  return useQuery({
    queryKey: customerContactKeys.stats(),
    queryFn: getCustomerContactStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateCustomerContact() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: CustomerContactInput) => createCustomerContact(input),
    onSuccess: () => client.invalidateQueries({ queryKey: customerContactKeys.all }),
  });
}

export function useUpdateCustomerContact(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: CustomerContactUpdateInput) => updateCustomerContact(id, input),
    onSuccess: (updated) => {
      client.setQueryData(customerContactKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: customerContactKeys.lists() });
    },
  });
}

export function useChangeCustomerContactStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeCustomerContactStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: customerContactKeys.all }),
  });
}

export function useDeleteCustomerContact() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteCustomerContact(id),
    onSuccess: () => client.invalidateQueries({ queryKey: customerContactKeys.all }),
  });
}

export function useBulkCreateCustomerContacts() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: CustomerContactInput[]) => bulkCreateCustomerContacts(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: customerContactKeys.all }),
  });
}

export function useExportCustomerContacts() {
  return useMutation({
    mutationFn: (filter: CustomerContactFilter) => exportCustomerContactsCsv(filter),
  });
}

export function useCustomerContactsByCustomerId(customerId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...customerContactKeys.all, 'by-customer_id', customerId, page, size],
    queryFn: () => listCustomerContactByCustomerId(customerId as string, page, size),
    enabled: Boolean(customerId),
  });
}
