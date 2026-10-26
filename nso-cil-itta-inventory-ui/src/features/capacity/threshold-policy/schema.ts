import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { THRESHOLD_POLICY_CRITICALITY_VALUES } from './types';

export const thresholdPolicyBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  policyName: z.string({ required_error: 'Policy Name is required' }).trim().min(1, 'Policy Name is required').max(255, 'Policy Name must be at most 255 characters'),
  metric: z.string({ required_error: 'Metric is required' }).trim().min(1, 'Metric is required').max(255, 'Metric must be at most 255 characters'),
  warnPct: z.number({ required_error: 'Warn Pct is required', invalid_type_error: 'Warn Pct must be a number' }).min(0, 'Warn Pct must not be negative'),
  critPct: z.number({ required_error: 'Crit Pct is required', invalid_type_error: 'Crit Pct must be a number' }).min(0, 'Crit Pct must not be negative'),
  evaluationWindowMinutes: z.number({ required_error: 'Evaluation Window Minutes is required', invalid_type_error: 'Evaluation Window Minutes must be a number' }).int('Evaluation Window Minutes must be a whole number').min(0, 'Evaluation Window Minutes must not be negative').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(THRESHOLD_POLICY_CRITICALITY_VALUES).optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
  notes: z.string().trim().max(4000, 'Notes must be at most 4000 characters').optional(),
});

export const thresholdPolicySchema = thresholdPolicyBaseSchema
;

export type ThresholdPolicyValid = z.infer<typeof thresholdPolicySchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanThresholdPolicyValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateThresholdPolicy(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = thresholdPolicySchema.safeParse(cleanThresholdPolicyValues(values as Record<string, unknown>));
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
