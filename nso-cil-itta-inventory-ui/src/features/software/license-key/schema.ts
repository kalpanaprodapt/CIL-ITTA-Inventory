import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { LICENSE_KEY_LIFECYCLE_STAGE_VALUES } from './types';

export const licenseKeyBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  poolId: z.string({ required_error: 'Pool Id is required' }).uuid('Pool Id must be a UUID'),
  keyValue: z.string({ required_error: 'Key Value is required' }).trim().min(1, 'Key Value is required').max(255, 'Key Value must be at most 255 characters'),
  feature: z.string({ required_error: 'Feature is required' }).trim().min(1, 'Feature is required').max(255, 'Feature must be at most 255 characters'),
  expiresOn: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Expires On must be YYYY-MM-DD').optional(),
  assignedDeviceId: z.string({ required_error: 'Assigned Device Id is required' }).uuid('Assigned Device Id must be a UUID').optional(),
  notes: z.string().trim().max(4000, 'Notes must be at most 4000 characters').optional(),
  effectiveFrom: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective From must be YYYY-MM-DD').optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(LICENSE_KEY_LIFECYCLE_STAGE_VALUES).optional(),
});

export const licenseKeySchema = licenseKeyBaseSchema
  .refine((v) => !v.effectiveFrom || !v.effectiveTo || v.effectiveTo >= v.effectiveFrom, {
    path: ['effectiveTo'],
    message: 'effectiveTo must not be earlier than effectiveFrom',
  });

export type LicenseKeyValid = z.infer<typeof licenseKeySchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanLicenseKeyValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateLicenseKey(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = licenseKeySchema.safeParse(cleanLicenseKeyValues(values as Record<string, unknown>));
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
