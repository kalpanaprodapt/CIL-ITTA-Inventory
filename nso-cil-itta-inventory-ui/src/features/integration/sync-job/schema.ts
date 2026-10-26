import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { SYNC_JOB_DIRECTION_VALUES, SYNC_JOB_LAST_STATUS_VALUES, SYNC_JOB_CRITICALITY_VALUES } from './types';

export const syncJobBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  endpointId: z.string({ required_error: 'Endpoint Id is required' }).uuid('Endpoint Id must be a UUID'),
  direction: z.enum(SYNC_JOB_DIRECTION_VALUES).optional(),
  scheduleCron: z.string().trim().max(255, 'Schedule Cron must be at most 255 characters').optional(),
  lastRunAt: z.string().regex(/^\d{4}-\d{2}-\d{2}T/, 'Last Run At must be an ISO date-time').optional(),
  lastStatus: z.enum(SYNC_JOB_LAST_STATUS_VALUES).optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(SYNC_JOB_CRITICALITY_VALUES).optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
  notes: z.string().trim().max(4000, 'Notes must be at most 4000 characters').optional(),
  effectiveFrom: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective From must be YYYY-MM-DD').optional(),
});

export const syncJobSchema = syncJobBaseSchema
;

export type SyncJobValid = z.infer<typeof syncJobSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanSyncJobValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateSyncJob(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = syncJobSchema.safeParse(cleanSyncJobValues(values as Record<string, unknown>));
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
