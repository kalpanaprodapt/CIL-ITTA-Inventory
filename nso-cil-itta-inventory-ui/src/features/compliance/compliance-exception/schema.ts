import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { COMPLIANCE_EXCEPTION_LIFECYCLE_STAGE_VALUES } from './types';

export const complianceExceptionBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  ruleId: z.string({ required_error: 'Rule Id is required' }).uuid('Rule Id must be a UUID'),
  targetRef: z.string({ required_error: 'Target Ref is required' }).trim().min(1, 'Target Ref is required').max(255, 'Target Ref must be at most 255 characters'),
  justification: z.string({ required_error: 'Justification is required' }).trim().min(1, 'Justification is required').max(4000, 'Justification must be at most 4000 characters'),
  approvedBy: z.string().trim().max(255, 'Approved By must be at most 255 characters').optional(),
  expiresOn: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Expires On must be YYYY-MM-DD').optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(COMPLIANCE_EXCEPTION_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
});

export const complianceExceptionSchema = complianceExceptionBaseSchema
;

export type ComplianceExceptionValid = z.infer<typeof complianceExceptionSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanComplianceExceptionValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateComplianceException(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = complianceExceptionSchema.safeParse(cleanComplianceExceptionValues(values as Record<string, unknown>));
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
