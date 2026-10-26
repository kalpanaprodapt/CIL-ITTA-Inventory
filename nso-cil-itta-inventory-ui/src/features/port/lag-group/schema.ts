import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { LAG_GROUP_LACP_MODE_VALUES, LAG_GROUP_LIFECYCLE_STAGE_VALUES } from './types';

export const lagGroupBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  deviceId: z.string({ required_error: 'Device Id is required' }).uuid('Device Id must be a UUID'),
  lagName: z.string({ required_error: 'Lag Name is required' }).trim().min(1, 'Lag Name is required').max(255, 'Lag Name must be at most 255 characters'),
  memberCount: z.number({ required_error: 'Member Count is required', invalid_type_error: 'Member Count must be a number' }).int('Member Count must be a whole number').min(0, 'Member Count must not be negative').optional(),
  lacpMode: z.enum(LAG_GROUP_LACP_MODE_VALUES).optional(),
  minLinks: z.number({ required_error: 'Min Links is required', invalid_type_error: 'Min Links must be a number' }).int('Min Links must be a whole number').min(0, 'Min Links must not be negative').optional(),
  effectiveFrom: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective From must be YYYY-MM-DD').optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(LAG_GROUP_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
});

export const lagGroupSchema = lagGroupBaseSchema
  .refine((v) => !v.effectiveFrom || !v.effectiveTo || v.effectiveTo >= v.effectiveFrom, {
    path: ['effectiveTo'],
    message: 'effectiveTo must not be earlier than effectiveFrom',
  });

export type LagGroupValid = z.infer<typeof lagGroupSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanLagGroupValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateLagGroup(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = lagGroupSchema.safeParse(cleanLagGroupValues(values as Record<string, unknown>));
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
