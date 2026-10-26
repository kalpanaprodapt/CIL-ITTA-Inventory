import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateIpAddresss,
  changeIpAddressStatus,
  createIpAddress,
  deleteIpAddress,
  exportIpAddresssCsv,
  getIpAddress,
  getIpAddressStatusStats,
  listIpAddressSummaries,
  searchIpAddresss,
  updateIpAddress,
  listIpAddressBySubnetId,
  listIpAddressByAssignedToId,
} from './api';
import type { IpAddressFilter, IpAddressInput, IpAddressUpdateInput } from './types';

export const ipAddressKeys = {
  all: ['ipAddress'] as const,
  lists: () => [...ipAddressKeys.all, 'list'] as const,
  list: (filter: IpAddressFilter, page: number, size: number, sort?: string) =>
    [...ipAddressKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...ipAddressKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...ipAddressKeys.all, 'summaries', status] as const,
  stats: () => [...ipAddressKeys.all, 'stats'] as const,
};

export function useIpAddressList(filter: IpAddressFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: ipAddressKeys.list(filter, page, size, sort),
    queryFn: () => searchIpAddresss(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useIpAddress(id: string | undefined) {
  return useQuery({
    queryKey: ipAddressKeys.detail(id ?? ''),
    queryFn: () => getIpAddress(id as string),
    enabled: Boolean(id),
  });
}

export function useIpAddressSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: ipAddressKeys.summaries(status),
    queryFn: () => listIpAddressSummaries(status),
    staleTime: 60_000,
  });
}

export function useIpAddressStatusStats() {
  return useQuery({
    queryKey: ipAddressKeys.stats(),
    queryFn: getIpAddressStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateIpAddress() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: IpAddressInput) => createIpAddress(input),
    onSuccess: () => client.invalidateQueries({ queryKey: ipAddressKeys.all }),
  });
}

export function useUpdateIpAddress(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: IpAddressUpdateInput) => updateIpAddress(id, input),
    onSuccess: (updated) => {
      client.setQueryData(ipAddressKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: ipAddressKeys.lists() });
    },
  });
}

export function useChangeIpAddressStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeIpAddressStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: ipAddressKeys.all }),
  });
}

export function useDeleteIpAddress() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteIpAddress(id),
    onSuccess: () => client.invalidateQueries({ queryKey: ipAddressKeys.all }),
  });
}

export function useBulkCreateIpAddresss() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: IpAddressInput[]) => bulkCreateIpAddresss(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: ipAddressKeys.all }),
  });
}

export function useExportIpAddresss() {
  return useMutation({
    mutationFn: (filter: IpAddressFilter) => exportIpAddresssCsv(filter),
  });
}

export function useIpAddresssBySubnetId(subnetId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...ipAddressKeys.all, 'by-subnet_id', subnetId, page, size],
    queryFn: () => listIpAddressBySubnetId(subnetId as string, page, size),
    enabled: Boolean(subnetId),
  });
}

export function useIpAddresssByAssignedToId(assignedToId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...ipAddressKeys.all, 'by-assigned_to_id', assignedToId, page, size],
    queryFn: () => listIpAddressByAssignedToId(assignedToId as string, page, size),
    enabled: Boolean(assignedToId),
  });
}
