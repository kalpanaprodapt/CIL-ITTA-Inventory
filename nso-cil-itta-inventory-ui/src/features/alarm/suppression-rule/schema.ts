import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { SUPPRESSION_RULE_CRITICALITY_VALUES } from './types';

export const suppressionRuleBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  ruleName: z.string({ required_error: 'Rule Name is required' }).trim().min(1, 'Rule Name is required').max(255, 'Rule Name must be at most 255 characters'),
  matchExpression: z.string({ required_error: 'Match Expression is required' }).trim().min(1, 'Match Expression is required').max(4000, 'Match Expression must be at most 4000 characters'),
  startsAt: z.string().regex(/^\d{4}-\d{2}-\d{2}T/, 'Starts At must be an ISO date-time').optional(),
  endsAt: z.string().regex(/^\d{4}-\d{2}-\d{2}T/, 'Ends At must be an ISO date-time').optional(),
  reason: z.string().trim().max(255, 'Reason must be at most 255 characters').optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(SUPPRESSION_RULE_CRITICALITY_VALUES).optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
});

export const suppressionRuleSchema = suppressionRuleBaseSchema
  .refine((v) => !v.startsAt || !v.endsAt || v.endsAt >= v.startsAt, {
    path: ['endsAt'],
    message: 'endsAt must not be earlier than startsAt',
  });

export type SuppressionRuleValid = z.infer<typeof suppressionRuleSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanSuppressionRuleValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateSuppressionRule(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = suppressionRuleSchema.safeParse(cleanSuppressionRuleValues(values as Record<string, unknown>));
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
