import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { SLA_PROFILE_CRITICALITY_VALUES } from './types';

export const slaProfileBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  profileName: z.string({ required_error: 'Profile Name is required' }).trim().min(1, 'Profile Name is required').max(255, 'Profile Name must be at most 255 characters'),
  availabilityPct: z.number({ required_error: 'Availability Pct is required', invalid_type_error: 'Availability Pct must be a number' }).min(0, 'Availability Pct must not be negative'),
  mttrMinutes: z.number({ required_error: 'Mttr Minutes is required', invalid_type_error: 'Mttr Minutes must be a number' }).int('Mttr Minutes must be a whole number').min(0, 'Mttr Minutes must not be negative').optional(),
  latencyMs: z.number({ required_error: 'Latency Ms is required', invalid_type_error: 'Latency Ms must be a number' }).min(0, 'Latency Ms must not be negative').optional(),
  jitterMs: z.number({ required_error: 'Jitter Ms is required', invalid_type_error: 'Jitter Ms must be a number' }).min(0, 'Jitter Ms must not be negative').optional(),
  creditPolicy: z.string().trim().max(4000, 'Credit Policy must be at most 4000 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(SLA_PROFILE_CRITICALITY_VALUES).optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
  notes: z.string().trim().max(4000, 'Notes must be at most 4000 characters').optional(),
  effectiveFrom: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective From must be YYYY-MM-DD').optional(),
});

export const slaProfileSchema = slaProfileBaseSchema
;

export type SlaProfileValid = z.infer<typeof slaProfileSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanSlaProfileValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateSlaProfile(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = slaProfileSchema.safeParse(cleanSlaProfileValues(values as Record<string, unknown>));
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
