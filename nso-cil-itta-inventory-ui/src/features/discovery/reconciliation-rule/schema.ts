import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { RECONCILIATION_RULE_ACTION_VALUES, RECONCILIATION_RULE_LIFECYCLE_STAGE_VALUES } from './types';

export const reconciliationRuleBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  ruleName: z.string({ required_error: 'Rule Name is required' }).trim().min(1, 'Rule Name is required').max(255, 'Rule Name must be at most 255 characters'),
  matchKey: z.string({ required_error: 'Match Key is required' }).trim().min(1, 'Match Key is required').max(255, 'Match Key must be at most 255 characters'),
  action: z.enum(RECONCILIATION_RULE_ACTION_VALUES).optional(),
  priority: z.number({ required_error: 'Priority is required', invalid_type_error: 'Priority must be a number' }).int('Priority must be a whole number').min(0, 'Priority must not be negative'),
  expression: z.string().trim().max(4000, 'Expression must be at most 4000 characters').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(RECONCILIATION_RULE_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
});

export const reconciliationRuleSchema = reconciliationRuleBaseSchema
;

export type ReconciliationRuleValid = z.infer<typeof reconciliationRuleSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanReconciliationRuleValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateReconciliationRule(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = reconciliationRuleSchema.safeParse(cleanReconciliationRuleValues(values as Record<string, unknown>));
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
