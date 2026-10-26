import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { POWER_FEED_FEED_TYPE_VALUES, POWER_FEED_CRITICALITY_VALUES } from './types';

export const powerFeedBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  roomId: z.string({ required_error: 'Room Id is required' }).uuid('Room Id must be a UUID'),
  feedType: z.enum(POWER_FEED_FEED_TYPE_VALUES).optional(),
  voltage: z.number({ required_error: 'Voltage is required', invalid_type_error: 'Voltage must be a number' }).min(0, 'Voltage must not be negative').optional(),
  amperage: z.number({ required_error: 'Amperage is required', invalid_type_error: 'Amperage must be a number' }).min(0, 'Amperage must not be negative').optional(),
  phase: z.string().trim().max(255, 'Phase must be at most 255 characters').optional(),
  breakerLabel: z.string().trim().max(255, 'Breaker Label must be at most 255 characters').optional(),
  redundancyGroup: z.string().trim().max(255, 'Redundancy Group must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(POWER_FEED_CRITICALITY_VALUES).optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
  notes: z.string().trim().max(4000, 'Notes must be at most 4000 characters').optional(),
  effectiveFrom: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective From must be YYYY-MM-DD').optional(),
});

export const powerFeedSchema = powerFeedBaseSchema
;

export type PowerFeedValid = z.infer<typeof powerFeedSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanPowerFeedValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validatePowerFeed(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = powerFeedSchema.safeParse(cleanPowerFeedValues(values as Record<string, unknown>));
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
