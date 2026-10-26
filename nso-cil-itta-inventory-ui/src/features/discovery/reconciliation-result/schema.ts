import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { RECONCILIATION_RESULT_OUTCOME_VALUES, RECONCILIATION_RESULT_CRITICALITY_VALUES } from './types';

export const reconciliationResultBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  jobId: z.string({ required_error: 'Job Id is required' }).uuid('Job Id must be a UUID'),
  ruleId: z.string({ required_error: 'Rule Id is required' }).uuid('Rule Id must be a UUID').optional(),
  outcome: z.enum(RECONCILIATION_RESULT_OUTCOME_VALUES).optional(),
  diffSummary: z.string().trim().max(4000, 'Diff Summary must be at most 4000 characters').optional(),
  resolvedBy: z.string().trim().max(255, 'Resolved By must be at most 255 characters').optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(RECONCILIATION_RESULT_CRITICALITY_VALUES).optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
});

export const reconciliationResultSchema = reconciliationResultBaseSchema
;

export type ReconciliationResultValid = z.infer<typeof reconciliationResultSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanReconciliationResultValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateReconciliationResult(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = reconciliationResultSchema.safeParse(cleanReconciliationResultValues(values as Record<string, unknown>));
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
