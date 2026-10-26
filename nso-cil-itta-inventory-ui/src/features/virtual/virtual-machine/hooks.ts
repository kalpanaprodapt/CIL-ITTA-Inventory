import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateVirtualMachines,
  changeVirtualMachineStatus,
  createVirtualMachine,
  deleteVirtualMachine,
  exportVirtualMachinesCsv,
  getVirtualMachine,
  getVirtualMachineStatusStats,
  listVirtualMachineSummaries,
  searchVirtualMachines,
  updateVirtualMachine,
  listVirtualMachineByHypervisorId,
} from './api';
import type { VirtualMachineFilter, VirtualMachineInput, VirtualMachineUpdateInput } from './types';

export const virtualMachineKeys = {
  all: ['virtualMachine'] as const,
  lists: () => [...virtualMachineKeys.all, 'list'] as const,
  list: (filter: VirtualMachineFilter, page: number, size: number, sort?: string) =>
    [...virtualMachineKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...virtualMachineKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...virtualMachineKeys.all, 'summaries', status] as const,
  stats: () => [...virtualMachineKeys.all, 'stats'] as const,
};

export function useVirtualMachineList(filter: VirtualMachineFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: virtualMachineKeys.list(filter, page, size, sort),
    queryFn: () => searchVirtualMachines(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useVirtualMachine(id: string | undefined) {
  return useQuery({
    queryKey: virtualMachineKeys.detail(id ?? ''),
    queryFn: () => getVirtualMachine(id as string),
    enabled: Boolean(id),
  });
}

export function useVirtualMachineSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: virtualMachineKeys.summaries(status),
    queryFn: () => listVirtualMachineSummaries(status),
    staleTime: 60_000,
  });
}

export function useVirtualMachineStatusStats() {
  return useQuery({
    queryKey: virtualMachineKeys.stats(),
    queryFn: getVirtualMachineStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateVirtualMachine() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: VirtualMachineInput) => createVirtualMachine(input),
    onSuccess: () => client.invalidateQueries({ queryKey: virtualMachineKeys.all }),
  });
}

export function useUpdateVirtualMachine(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: VirtualMachineUpdateInput) => updateVirtualMachine(id, input),
    onSuccess: (updated) => {
      client.setQueryData(virtualMachineKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: virtualMachineKeys.lists() });
    },
  });
}

export function useChangeVirtualMachineStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeVirtualMachineStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: virtualMachineKeys.all }),
  });
}

export function useDeleteVirtualMachine() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteVirtualMachine(id),
    onSuccess: () => client.invalidateQueries({ queryKey: virtualMachineKeys.all }),
  });
}

export function useBulkCreateVirtualMachines() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: VirtualMachineInput[]) => bulkCreateVirtualMachines(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: virtualMachineKeys.all }),
  });
}

export function useExportVirtualMachines() {
  return useMutation({
    mutationFn: (filter: VirtualMachineFilter) => exportVirtualMachinesCsv(filter),
  });
}

export function useVirtualMachinesByHypervisorId(hypervisorId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...virtualMachineKeys.all, 'by-hypervisor_id', hypervisorId, page, size],
    queryFn: () => listVirtualMachineByHypervisorId(hypervisorId as string, page, size),
    enabled: Boolean(hypervisorId),
  });
}
