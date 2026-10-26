import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { QOS_POLICY_LIFECYCLE_STAGE_VALUES } from './types';

export const qosPolicyBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  policyName: z.string({ required_error: 'Policy Name is required' }).trim().min(1, 'Policy Name is required').max(255, 'Policy Name must be at most 255 characters'),
  classCount: z.number({ required_error: 'Class Count is required', invalid_type_error: 'Class Count must be a number' }).int('Class Count must be a whole number').min(0, 'Class Count must not be negative'),
  cirMbps: z.number({ required_error: 'Cir Mbps is required', invalid_type_error: 'Cir Mbps must be a number' }).int('Cir Mbps must be a whole number').min(0, 'Cir Mbps must not be negative').optional(),
  pirMbps: z.number({ required_error: 'Pir Mbps is required', invalid_type_error: 'Pir Mbps must be a number' }).int('Pir Mbps must be a whole number').min(0, 'Pir Mbps must not be negative').optional(),
  markingScheme: z.string().trim().max(255, 'Marking Scheme must be at most 255 characters').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(QOS_POLICY_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
});

export const qosPolicySchema = qosPolicyBaseSchema
;

export type QosPolicyValid = z.infer<typeof qosPolicySchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanQosPolicyValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateQosPolicy(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = qosPolicySchema.safeParse(cleanQosPolicyValues(values as Record<string, unknown>));
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
