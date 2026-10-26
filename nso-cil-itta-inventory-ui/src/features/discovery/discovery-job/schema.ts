import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { DISCOVERY_JOB_JOB_STATE_VALUES, DISCOVERY_JOB_CRITICALITY_VALUES } from './types';

export const discoveryJobBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  profileId: z.string({ required_error: 'Profile Id is required' }).uuid('Profile Id must be a UUID'),
  startedAt: z.string().regex(/^\d{4}-\d{2}-\d{2}T/, 'Started At must be an ISO date-time').optional(),
  finishedAt: z.string().regex(/^\d{4}-\d{2}-\d{2}T/, 'Finished At must be an ISO date-time').optional(),
  jobState: z.enum(DISCOVERY_JOB_JOB_STATE_VALUES).optional(),
  assetsFound: z.number({ required_error: 'Assets Found is required', invalid_type_error: 'Assets Found must be a number' }).int('Assets Found must be a whole number').min(0, 'Assets Found must not be negative').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(DISCOVERY_JOB_CRITICALITY_VALUES).optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
  notes: z.string().trim().max(4000, 'Notes must be at most 4000 characters').optional(),
  effectiveFrom: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective From must be YYYY-MM-DD').optional(),
});

export const discoveryJobSchema = discoveryJobBaseSchema
  .refine((v) => !v.startedAt || !v.finishedAt || v.finishedAt >= v.startedAt, {
    path: ['finishedAt'],
    message: 'finishedAt must not be earlier than startedAt',
  });

export type DiscoveryJobValid = z.infer<typeof discoveryJobSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanDiscoveryJobValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateDiscoveryJob(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = discoveryJobSchema.safeParse(cleanDiscoveryJobValues(values as Record<string, unknown>));
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
