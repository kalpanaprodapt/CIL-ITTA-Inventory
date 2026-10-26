import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateIpSubnets,
  changeIpSubnetStatus,
  createIpSubnet,
  deleteIpSubnet,
  exportIpSubnetsCsv,
  getIpSubnet,
  getIpSubnetStatusStats,
  listIpSubnetSummaries,
  searchIpSubnets,
  updateIpSubnet,
  listIpSubnetByVrfId,
  listIpSubnetByVlanId,
} from './api';
import type { IpSubnetFilter, IpSubnetInput, IpSubnetUpdateInput } from './types';

export const ipSubnetKeys = {
  all: ['ipSubnet'] as const,
  lists: () => [...ipSubnetKeys.all, 'list'] as const,
  list: (filter: IpSubnetFilter, page: number, size: number, sort?: string) =>
    [...ipSubnetKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...ipSubnetKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...ipSubnetKeys.all, 'summaries', status] as const,
  stats: () => [...ipSubnetKeys.all, 'stats'] as const,
};

export function useIpSubnetList(filter: IpSubnetFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: ipSubnetKeys.list(filter, page, size, sort),
    queryFn: () => searchIpSubnets(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useIpSubnet(id: string | undefined) {
  return useQuery({
    queryKey: ipSubnetKeys.detail(id ?? ''),
    queryFn: () => getIpSubnet(id as string),
    enabled: Boolean(id),
  });
}

export function useIpSubnetSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: ipSubnetKeys.summaries(status),
    queryFn: () => listIpSubnetSummaries(status),
    staleTime: 60_000,
  });
}

export function useIpSubnetStatusStats() {
  return useQuery({
    queryKey: ipSubnetKeys.stats(),
    queryFn: getIpSubnetStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateIpSubnet() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: IpSubnetInput) => createIpSubnet(input),
    onSuccess: () => client.invalidateQueries({ queryKey: ipSubnetKeys.all }),
  });
}

export function useUpdateIpSubnet(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: IpSubnetUpdateInput) => updateIpSubnet(id, input),
    onSuccess: (updated) => {
      client.setQueryData(ipSubnetKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: ipSubnetKeys.lists() });
    },
  });
}

export function useChangeIpSubnetStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeIpSubnetStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: ipSubnetKeys.all }),
  });
}

export function useDeleteIpSubnet() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteIpSubnet(id),
    onSuccess: () => client.invalidateQueries({ queryKey: ipSubnetKeys.all }),
  });
}

export function useBulkCreateIpSubnets() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: IpSubnetInput[]) => bulkCreateIpSubnets(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: ipSubnetKeys.all }),
  });
}

export function useExportIpSubnets() {
  return useMutation({
    mutationFn: (filter: IpSubnetFilter) => exportIpSubnetsCsv(filter),
  });
}

export function useIpSubnetsByVrfId(vrfId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...ipSubnetKeys.all, 'by-vrf_id', vrfId, page, size],
    queryFn: () => listIpSubnetByVrfId(vrfId as string, page, size),
    enabled: Boolean(vrfId),
  });
}

export function useIpSubnetsByVlanId(vlanId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...ipSubnetKeys.all, 'by-vlan_id', vlanId, page, size],
    queryFn: () => listIpSubnetByVlanId(vlanId as string, page, size),
    enabled: Boolean(vlanId),
  });
}
