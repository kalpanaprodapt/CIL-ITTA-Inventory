import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateSoftwareImages,
  changeSoftwareImageStatus,
  createSoftwareImage,
  deleteSoftwareImage,
  exportSoftwareImagesCsv,
  getSoftwareImage,
  getSoftwareImageStatusStats,
  listSoftwareImageSummaries,
  searchSoftwareImages,
  updateSoftwareImage,
  listSoftwareImageByVendorId,
} from './api';
import type { SoftwareImageFilter, SoftwareImageInput, SoftwareImageUpdateInput } from './types';

export const softwareImageKeys = {
  all: ['softwareImage'] as const,
  lists: () => [...softwareImageKeys.all, 'list'] as const,
  list: (filter: SoftwareImageFilter, page: number, size: number, sort?: string) =>
    [...softwareImageKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...softwareImageKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...softwareImageKeys.all, 'summaries', status] as const,
  stats: () => [...softwareImageKeys.all, 'stats'] as const,
};

export function useSoftwareImageList(filter: SoftwareImageFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: softwareImageKeys.list(filter, page, size, sort),
    queryFn: () => searchSoftwareImages(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useSoftwareImage(id: string | undefined) {
  return useQuery({
    queryKey: softwareImageKeys.detail(id ?? ''),
    queryFn: () => getSoftwareImage(id as string),
    enabled: Boolean(id),
  });
}

export function useSoftwareImageSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: softwareImageKeys.summaries(status),
    queryFn: () => listSoftwareImageSummaries(status),
    staleTime: 60_000,
  });
}

export function useSoftwareImageStatusStats() {
  return useQuery({
    queryKey: softwareImageKeys.stats(),
    queryFn: getSoftwareImageStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateSoftwareImage() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: SoftwareImageInput) => createSoftwareImage(input),
    onSuccess: () => client.invalidateQueries({ queryKey: softwareImageKeys.all }),
  });
}

export function useUpdateSoftwareImage(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: SoftwareImageUpdateInput) => updateSoftwareImage(id, input),
    onSuccess: (updated) => {
      client.setQueryData(softwareImageKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: softwareImageKeys.lists() });
    },
  });
}

export function useChangeSoftwareImageStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeSoftwareImageStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: softwareImageKeys.all }),
  });
}

export function useDeleteSoftwareImage() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteSoftwareImage(id),
    onSuccess: () => client.invalidateQueries({ queryKey: softwareImageKeys.all }),
  });
}

export function useBulkCreateSoftwareImages() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: SoftwareImageInput[]) => bulkCreateSoftwareImages(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: softwareImageKeys.all }),
  });
}

export function useExportSoftwareImages() {
  return useMutation({
    mutationFn: (filter: SoftwareImageFilter) => exportSoftwareImagesCsv(filter),
  });
}

export function useSoftwareImagesByVendorId(vendorId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...softwareImageKeys.all, 'by-vendor_id', vendorId, page, size],
    queryFn: () => listSoftwareImageByVendorId(vendorId as string, page, size),
    enabled: Boolean(vendorId),
  });
}
