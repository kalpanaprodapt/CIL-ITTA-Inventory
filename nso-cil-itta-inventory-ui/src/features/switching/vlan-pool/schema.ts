import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { VLAN_POOL_LIFECYCLE_STAGE_VALUES } from './types';

export const vlanPoolBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  poolName: z.string({ required_error: 'Pool Name is required' }).trim().min(1, 'Pool Name is required').max(255, 'Pool Name must be at most 255 characters'),
  rangeStart: z.number({ required_error: 'Range Start is required', invalid_type_error: 'Range Start must be a number' }).int('Range Start must be a whole number').min(0, 'Range Start must not be negative'),
  rangeEnd: z.number({ required_error: 'Range End is required', invalid_type_error: 'Range End must be a number' }).int('Range End must be a whole number').min(0, 'Range End must not be negative'),
  allocated: z.number({ required_error: 'Allocated is required', invalid_type_error: 'Allocated must be a number' }).int('Allocated must be a whole number').min(0, 'Allocated must not be negative').optional(),
  scope: z.string().trim().max(255, 'Scope must be at most 255 characters').optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(VLAN_POOL_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
});

export const vlanPoolSchema = vlanPoolBaseSchema
  .refine((v) => !v.rangeStart || !v.rangeEnd || v.rangeEnd >= v.rangeStart, {
    path: ['rangeEnd'],
    message: 'rangeEnd must not be earlier than rangeStart',
  });

export type VlanPoolValid = z.infer<typeof vlanPoolSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanVlanPoolValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateVlanPool(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = vlanPoolSchema.safeParse(cleanVlanPoolValues(values as Record<string, unknown>));
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
