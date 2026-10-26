import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateLicensePools,
  changeLicensePoolStatus,
  createLicensePool,
  deleteLicensePool,
  exportLicensePoolsCsv,
  getLicensePool,
  getLicensePoolStatusStats,
  listLicensePoolSummaries,
  searchLicensePools,
  updateLicensePool,
  listLicensePoolByVendorId,
} from './api';
import type { LicensePoolFilter, LicensePoolInput, LicensePoolUpdateInput } from './types';

export const licensePoolKeys = {
  all: ['licensePool'] as const,
  lists: () => [...licensePoolKeys.all, 'list'] as const,
  list: (filter: LicensePoolFilter, page: number, size: number, sort?: string) =>
    [...licensePoolKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...licensePoolKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...licensePoolKeys.all, 'summaries', status] as const,
  stats: () => [...licensePoolKeys.all, 'stats'] as const,
};

export function useLicensePoolList(filter: LicensePoolFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: licensePoolKeys.list(filter, page, size, sort),
    queryFn: () => searchLicensePools(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useLicensePool(id: string | undefined) {
  return useQuery({
    queryKey: licensePoolKeys.detail(id ?? ''),
    queryFn: () => getLicensePool(id as string),
    enabled: Boolean(id),
  });
}

export function useLicensePoolSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: licensePoolKeys.summaries(status),
    queryFn: () => listLicensePoolSummaries(status),
    staleTime: 60_000,
  });
}

export function useLicensePoolStatusStats() {
  return useQuery({
    queryKey: licensePoolKeys.stats(),
    queryFn: getLicensePoolStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateLicensePool() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: LicensePoolInput) => createLicensePool(input),
    onSuccess: () => client.invalidateQueries({ queryKey: licensePoolKeys.all }),
  });
}

export function useUpdateLicensePool(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: LicensePoolUpdateInput) => updateLicensePool(id, input),
    onSuccess: (updated) => {
      client.setQueryData(licensePoolKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: licensePoolKeys.lists() });
    },
  });
}

export function useChangeLicensePoolStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeLicensePoolStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: licensePoolKeys.all }),
  });
}

export function useDeleteLicensePool() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteLicensePool(id),
    onSuccess: () => client.invalidateQueries({ queryKey: licensePoolKeys.all }),
  });
}

export function useBulkCreateLicensePools() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: LicensePoolInput[]) => bulkCreateLicensePools(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: licensePoolKeys.all }),
  });
}

export function useExportLicensePools() {
  return useMutation({
    mutationFn: (filter: LicensePoolFilter) => exportLicensePoolsCsv(filter),
  });
}

export function useLicensePoolsByVendorId(vendorId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...licensePoolKeys.all, 'by-vendor_id', vendorId, page, size],
    queryFn: () => listLicensePoolByVendorId(vendorId as string, page, size),
    enabled: Boolean(vendorId),
  });
}
