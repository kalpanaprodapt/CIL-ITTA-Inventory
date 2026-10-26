import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateContracts,
  changeContractStatus,
  createContract,
  deleteContract,
  exportContractsCsv,
  getContract,
  getContractStatusStats,
  listContractSummaries,
  searchContracts,
  updateContract,
  listContractByCustomerId,
} from './api';
import type { ContractFilter, ContractInput, ContractUpdateInput } from './types';

export const contractKeys = {
  all: ['contract'] as const,
  lists: () => [...contractKeys.all, 'list'] as const,
  list: (filter: ContractFilter, page: number, size: number, sort?: string) =>
    [...contractKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...contractKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...contractKeys.all, 'summaries', status] as const,
  stats: () => [...contractKeys.all, 'stats'] as const,
};

export function useContractList(filter: ContractFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: contractKeys.list(filter, page, size, sort),
    queryFn: () => searchContracts(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useContract(id: string | undefined) {
  return useQuery({
    queryKey: contractKeys.detail(id ?? ''),
    queryFn: () => getContract(id as string),
    enabled: Boolean(id),
  });
}

export function useContractSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: contractKeys.summaries(status),
    queryFn: () => listContractSummaries(status),
    staleTime: 60_000,
  });
}

export function useContractStatusStats() {
  return useQuery({
    queryKey: contractKeys.stats(),
    queryFn: getContractStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateContract() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ContractInput) => createContract(input),
    onSuccess: () => client.invalidateQueries({ queryKey: contractKeys.all }),
  });
}

export function useUpdateContract(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ContractUpdateInput) => updateContract(id, input),
    onSuccess: (updated) => {
      client.setQueryData(contractKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: contractKeys.lists() });
    },
  });
}

export function useChangeContractStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeContractStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: contractKeys.all }),
  });
}

export function useDeleteContract() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteContract(id),
    onSuccess: () => client.invalidateQueries({ queryKey: contractKeys.all }),
  });
}

export function useBulkCreateContracts() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: ContractInput[]) => bulkCreateContracts(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: contractKeys.all }),
  });
}

export function useExportContracts() {
  return useMutation({
    mutationFn: (filter: ContractFilter) => exportContractsCsv(filter),
  });
}

export function useContractsByCustomerId(customerId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...contractKeys.all, 'by-customer_id', customerId, page, size],
    queryFn: () => listContractByCustomerId(customerId as string, page, size),
    enabled: Boolean(customerId),
  });
}
