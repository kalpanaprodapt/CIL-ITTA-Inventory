import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateK8sClusters,
  changeK8sClusterStatus,
  createK8sCluster,
  deleteK8sCluster,
  exportK8sClustersCsv,
  getK8sCluster,
  getK8sClusterStatusStats,
  listK8sClusterSummaries,
  searchK8sClusters,
  updateK8sCluster,
  listK8sClusterByCloudAccountId,
} from './api';
import type { K8sClusterFilter, K8sClusterInput, K8sClusterUpdateInput } from './types';

export const k8sClusterKeys = {
  all: ['k8sCluster'] as const,
  lists: () => [...k8sClusterKeys.all, 'list'] as const,
  list: (filter: K8sClusterFilter, page: number, size: number, sort?: string) =>
    [...k8sClusterKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...k8sClusterKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...k8sClusterKeys.all, 'summaries', status] as const,
  stats: () => [...k8sClusterKeys.all, 'stats'] as const,
};

export function useK8sClusterList(filter: K8sClusterFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: k8sClusterKeys.list(filter, page, size, sort),
    queryFn: () => searchK8sClusters(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useK8sCluster(id: string | undefined) {
  return useQuery({
    queryKey: k8sClusterKeys.detail(id ?? ''),
    queryFn: () => getK8sCluster(id as string),
    enabled: Boolean(id),
  });
}

export function useK8sClusterSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: k8sClusterKeys.summaries(status),
    queryFn: () => listK8sClusterSummaries(status),
    staleTime: 60_000,
  });
}

export function useK8sClusterStatusStats() {
  return useQuery({
    queryKey: k8sClusterKeys.stats(),
    queryFn: getK8sClusterStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateK8sCluster() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: K8sClusterInput) => createK8sCluster(input),
    onSuccess: () => client.invalidateQueries({ queryKey: k8sClusterKeys.all }),
  });
}

export function useUpdateK8sCluster(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: K8sClusterUpdateInput) => updateK8sCluster(id, input),
    onSuccess: (updated) => {
      client.setQueryData(k8sClusterKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: k8sClusterKeys.lists() });
    },
  });
}

export function useChangeK8sClusterStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeK8sClusterStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: k8sClusterKeys.all }),
  });
}

export function useDeleteK8sCluster() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteK8sCluster(id),
    onSuccess: () => client.invalidateQueries({ queryKey: k8sClusterKeys.all }),
  });
}

export function useBulkCreateK8sClusters() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: K8sClusterInput[]) => bulkCreateK8sClusters(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: k8sClusterKeys.all }),
  });
}

export function useExportK8sClusters() {
  return useMutation({
    mutationFn: (filter: K8sClusterFilter) => exportK8sClustersCsv(filter),
  });
}

export function useK8sClustersByCloudAccountId(cloudAccountId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...k8sClusterKeys.all, 'by-cloud_account_id', cloudAccountId, page, size],
    queryFn: () => listK8sClusterByCloudAccountId(cloudAccountId as string, page, size),
    enabled: Boolean(cloudAccountId),
  });
}
