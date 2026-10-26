import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { VLAN_VLAN_TYPE_VALUES, VLAN_LIFECYCLE_STAGE_VALUES } from './types';

export const vlanBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  vlanId: z.number({ required_error: 'Vlan Id is required', invalid_type_error: 'Vlan Id must be a number' }).int('Vlan Id must be a whole number').min(0, 'Vlan Id must not be negative'),
  vlanName: z.string({ required_error: 'Vlan Name is required' }).trim().min(1, 'Vlan Name is required').max(255, 'Vlan Name must be at most 255 characters'),
  vlanType: z.enum(VLAN_VLAN_TYPE_VALUES).optional(),
  poolId: z.string({ required_error: 'Pool Id is required' }).uuid('Pool Id must be a UUID').optional(),
  stpMode: z.string().trim().max(255, 'Stp Mode must be at most 255 characters').optional(),
  notes: z.string().trim().max(4000, 'Notes must be at most 4000 characters').optional(),
  effectiveFrom: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective From must be YYYY-MM-DD').optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(VLAN_LIFECYCLE_STAGE_VALUES).optional(),
});

export const vlanSchema = vlanBaseSchema
  .refine((v) => !v.effectiveFrom || !v.effectiveTo || v.effectiveTo >= v.effectiveFrom, {
    path: ['effectiveTo'],
    message: 'effectiveTo must not be earlier than effectiveFrom',
  });

export type VlanValid = z.infer<typeof vlanSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanVlanValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateVlan(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = vlanSchema.safeParse(cleanVlanValues(values as Record<string, unknown>));
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
