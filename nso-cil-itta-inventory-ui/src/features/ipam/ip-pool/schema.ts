import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { IP_POOL_POOL_TYPE_VALUES, IP_POOL_LIFECYCLE_STAGE_VALUES } from './types';

export const ipPoolBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  subnetId: z.string({ required_error: 'Subnet Id is required' }).uuid('Subnet Id must be a UUID'),
  rangeStart: z.string({ required_error: 'Range Start is required' }).trim().min(1, 'Range Start is required').max(255, 'Range Start must be at most 255 characters'),
  rangeEnd: z.string({ required_error: 'Range End is required' }).trim().min(1, 'Range End is required').max(255, 'Range End must be at most 255 characters'),
  poolType: z.enum(IP_POOL_POOL_TYPE_VALUES).optional(),
  leaseSeconds: z.number({ required_error: 'Lease Seconds is required', invalid_type_error: 'Lease Seconds must be a number' }).int('Lease Seconds must be a whole number').min(0, 'Lease Seconds must not be negative').optional(),
  effectiveFrom: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective From must be YYYY-MM-DD').optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(IP_POOL_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
});

export const ipPoolSchema = ipPoolBaseSchema
  .refine((v) => !v.effectiveFrom || !v.effectiveTo || v.effectiveTo >= v.effectiveFrom, {
    path: ['effectiveTo'],
    message: 'effectiveTo must not be earlier than effectiveFrom',
  });

export type IpPoolValid = z.infer<typeof ipPoolSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanIpPoolValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateIpPool(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = ipPoolSchema.safeParse(cleanIpPoolValues(values as Record<string, unknown>));
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
