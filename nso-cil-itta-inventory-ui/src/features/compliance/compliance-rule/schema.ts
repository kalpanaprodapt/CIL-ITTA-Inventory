import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { COMPLIANCE_RULE_FRAMEWORK_VALUES, COMPLIANCE_RULE_SEVERITY_VALUES, COMPLIANCE_RULE_CRITICALITY_VALUES } from './types';

export const complianceRuleBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  ruleCode: z.string({ required_error: 'Rule Code is required' }).trim().min(1, 'Rule Code is required').max(255, 'Rule Code must be at most 255 characters'),
  framework: z.enum(COMPLIANCE_RULE_FRAMEWORK_VALUES).optional(),
  severity: z.enum(COMPLIANCE_RULE_SEVERITY_VALUES).optional(),
  expression: z.string().trim().max(4000, 'Expression must be at most 4000 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(COMPLIANCE_RULE_CRITICALITY_VALUES).optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
  notes: z.string().trim().max(4000, 'Notes must be at most 4000 characters').optional(),
});

export const complianceRuleSchema = complianceRuleBaseSchema
;

export type ComplianceRuleValid = z.infer<typeof complianceRuleSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanComplianceRuleValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateComplianceRule(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = complianceRuleSchema.safeParse(cleanComplianceRuleValues(values as Record<string, unknown>));
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
