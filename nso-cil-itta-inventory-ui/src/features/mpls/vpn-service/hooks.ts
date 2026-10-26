import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateVpnServices,
  changeVpnServiceStatus,
  createVpnService,
  deleteVpnService,
  exportVpnServicesCsv,
  getVpnService,
  getVpnServiceStatusStats,
  listVpnServiceSummaries,
  searchVpnServices,
  updateVpnService,
  listVpnServiceByVrfId,
  listVpnServiceByCustomerId,
} from './api';
import type { VpnServiceFilter, VpnServiceInput, VpnServiceUpdateInput } from './types';

export const vpnServiceKeys = {
  all: ['vpnService'] as const,
  lists: () => [...vpnServiceKeys.all, 'list'] as const,
  list: (filter: VpnServiceFilter, page: number, size: number, sort?: string) =>
    [...vpnServiceKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...vpnServiceKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...vpnServiceKeys.all, 'summaries', status] as const,
  stats: () => [...vpnServiceKeys.all, 'stats'] as const,
};

export function useVpnServiceList(filter: VpnServiceFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: vpnServiceKeys.list(filter, page, size, sort),
    queryFn: () => searchVpnServices(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useVpnService(id: string | undefined) {
  return useQuery({
    queryKey: vpnServiceKeys.detail(id ?? ''),
    queryFn: () => getVpnService(id as string),
    enabled: Boolean(id),
  });
}

export function useVpnServiceSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: vpnServiceKeys.summaries(status),
    queryFn: () => listVpnServiceSummaries(status),
    staleTime: 60_000,
  });
}

export function useVpnServiceStatusStats() {
  return useQuery({
    queryKey: vpnServiceKeys.stats(),
    queryFn: getVpnServiceStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateVpnService() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: VpnServiceInput) => createVpnService(input),
    onSuccess: () => client.invalidateQueries({ queryKey: vpnServiceKeys.all }),
  });
}

export function useUpdateVpnService(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: VpnServiceUpdateInput) => updateVpnService(id, input),
    onSuccess: (updated) => {
      client.setQueryData(vpnServiceKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: vpnServiceKeys.lists() });
    },
  });
}

export function useChangeVpnServiceStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeVpnServiceStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: vpnServiceKeys.all }),
  });
}

export function useDeleteVpnService() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteVpnService(id),
    onSuccess: () => client.invalidateQueries({ queryKey: vpnServiceKeys.all }),
  });
}

export function useBulkCreateVpnServices() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: VpnServiceInput[]) => bulkCreateVpnServices(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: vpnServiceKeys.all }),
  });
}

export function useExportVpnServices() {
  return useMutation({
    mutationFn: (filter: VpnServiceFilter) => exportVpnServicesCsv(filter),
  });
}

export function useVpnServicesByVrfId(vrfId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...vpnServiceKeys.all, 'by-vrf_id', vrfId, page, size],
    queryFn: () => listVpnServiceByVrfId(vrfId as string, page, size),
    enabled: Boolean(vrfId),
  });
}

export function useVpnServicesByCustomerId(customerId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...vpnServiceKeys.all, 'by-customer_id', customerId, page, size],
    queryFn: () => listVpnServiceByCustomerId(customerId as string, page, size),
    enabled: Boolean(customerId),
  });
}
