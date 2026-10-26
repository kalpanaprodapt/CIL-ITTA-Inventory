import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateLicenseKeys,
  changeLicenseKeyStatus,
  createLicenseKey,
  deleteLicenseKey,
  exportLicenseKeysCsv,
  getLicenseKey,
  getLicenseKeyStatusStats,
  listLicenseKeySummaries,
  searchLicenseKeys,
  updateLicenseKey,
  listLicenseKeyByPoolId,
  listLicenseKeyByAssignedDeviceId,
} from './api';
import type { LicenseKeyFilter, LicenseKeyInput, LicenseKeyUpdateInput } from './types';

export const licenseKeyKeys = {
  all: ['licenseKey'] as const,
  lists: () => [...licenseKeyKeys.all, 'list'] as const,
  list: (filter: LicenseKeyFilter, page: number, size: number, sort?: string) =>
    [...licenseKeyKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...licenseKeyKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...licenseKeyKeys.all, 'summaries', status] as const,
  stats: () => [...licenseKeyKeys.all, 'stats'] as const,
};

export function useLicenseKeyList(filter: LicenseKeyFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: licenseKeyKeys.list(filter, page, size, sort),
    queryFn: () => searchLicenseKeys(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useLicenseKey(id: string | undefined) {
  return useQuery({
    queryKey: licenseKeyKeys.detail(id ?? ''),
    queryFn: () => getLicenseKey(id as string),
    enabled: Boolean(id),
  });
}

export function useLicenseKeySummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: licenseKeyKeys.summaries(status),
    queryFn: () => listLicenseKeySummaries(status),
    staleTime: 60_000,
  });
}

export function useLicenseKeyStatusStats() {
  return useQuery({
    queryKey: licenseKeyKeys.stats(),
    queryFn: getLicenseKeyStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateLicenseKey() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: LicenseKeyInput) => createLicenseKey(input),
    onSuccess: () => client.invalidateQueries({ queryKey: licenseKeyKeys.all }),
  });
}

export function useUpdateLicenseKey(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: LicenseKeyUpdateInput) => updateLicenseKey(id, input),
    onSuccess: (updated) => {
      client.setQueryData(licenseKeyKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: licenseKeyKeys.lists() });
    },
  });
}

export function useChangeLicenseKeyStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeLicenseKeyStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: licenseKeyKeys.all }),
  });
}

export function useDeleteLicenseKey() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteLicenseKey(id),
    onSuccess: () => client.invalidateQueries({ queryKey: licenseKeyKeys.all }),
  });
}

export function useBulkCreateLicenseKeys() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: LicenseKeyInput[]) => bulkCreateLicenseKeys(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: licenseKeyKeys.all }),
  });
}

export function useExportLicenseKeys() {
  return useMutation({
    mutationFn: (filter: LicenseKeyFilter) => exportLicenseKeysCsv(filter),
  });
}

export function useLicenseKeysByPoolId(poolId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...licenseKeyKeys.all, 'by-pool_id', poolId, page, size],
    queryFn: () => listLicenseKeyByPoolId(poolId as string, page, size),
    enabled: Boolean(poolId),
  });
}

export function useLicenseKeysByAssignedDeviceId(assignedDeviceId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...licenseKeyKeys.all, 'by-assigned_device_id', assignedDeviceId, page, size],
    queryFn: () => listLicenseKeyByAssignedDeviceId(assignedDeviceId as string, page, size),
    enabled: Boolean(assignedDeviceId),
  });
}
