import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { TOPOLOGY_VIEW_VIEW_TYPE_VALUES, TOPOLOGY_VIEW_LIFECYCLE_STAGE_VALUES } from './types';

export const topologyViewBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  viewName: z.string({ required_error: 'View Name is required' }).trim().min(1, 'View Name is required').max(255, 'View Name must be at most 255 characters'),
  viewType: z.enum(TOPOLOGY_VIEW_VIEW_TYPE_VALUES).optional(),
  scopeFilter: z.string().trim().max(255, 'Scope Filter must be at most 255 characters').optional(),
  refreshedAt: z.string().regex(/^\d{4}-\d{2}-\d{2}T/, 'Refreshed At must be an ISO date-time').optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(TOPOLOGY_VIEW_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
});

export const topologyViewSchema = topologyViewBaseSchema
;

export type TopologyViewValid = z.infer<typeof topologyViewSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanTopologyViewValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateTopologyView(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = topologyViewSchema.safeParse(cleanTopologyViewValues(values as Record<string, unknown>));
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
