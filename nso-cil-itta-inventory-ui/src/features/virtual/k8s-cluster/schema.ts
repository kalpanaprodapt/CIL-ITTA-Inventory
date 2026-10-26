import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { K8S_CLUSTER_LIFECYCLE_STAGE_VALUES } from './types';

export const k8sClusterBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  clusterName: z.string({ required_error: 'Cluster Name is required' }).trim().min(1, 'Cluster Name is required').max(255, 'Cluster Name must be at most 255 characters'),
  clusterVersion: z.string({ required_error: 'Cluster Version is required' }).trim().min(1, 'Cluster Version is required').max(255, 'Cluster Version must be at most 255 characters'),
  nodeCount: z.number({ required_error: 'Node Count is required', invalid_type_error: 'Node Count must be a number' }).int('Node Count must be a whole number').min(0, 'Node Count must not be negative'),
  cloudAccountId: z.string({ required_error: 'Cloud Account Id is required' }).uuid('Cloud Account Id must be a UUID').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  apiEndpoint: z.string().trim().max(255, 'Api Endpoint must be at most 255 characters').optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(K8S_CLUSTER_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
});

export const k8sClusterSchema = k8sClusterBaseSchema
;

export type K8sClusterValid = z.infer<typeof k8sClusterSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanK8sClusterValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateK8sCluster(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = k8sClusterSchema.safeParse(cleanK8sClusterValues(values as Record<string, unknown>));
  if (result.success) {
    return { ok: true, errors: {} };
  }
  const errors: Record<string, string> = {};
  for (const issue of result.error.issues) {
    const key = String(issue.path[0] ?? '_');
    if (!errors[key]) {
      errors[key] = issue.message;
    }
  }
  return { ok: false, errors };
}
