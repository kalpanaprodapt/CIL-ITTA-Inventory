import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { LICENSE_POOL_LIFECYCLE_STAGE_VALUES } from './types';

export const licensePoolBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  vendorId: z.string({ required_error: 'Vendor Id is required' }).uuid('Vendor Id must be a UUID'),
  poolName: z.string({ required_error: 'Pool Name is required' }).trim().min(1, 'Pool Name is required').max(255, 'Pool Name must be at most 255 characters'),
  totalSeats: z.number({ required_error: 'Total Seats is required', invalid_type_error: 'Total Seats must be a number' }).int('Total Seats must be a whole number').min(0, 'Total Seats must not be negative'),
  usedSeats: z.number({ required_error: 'Used Seats is required', invalid_type_error: 'Used Seats must be a number' }).int('Used Seats must be a whole number').min(0, 'Used Seats must not be negative').optional(),
  renewalDate: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Renewal Date must be YYYY-MM-DD').optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(LICENSE_POOL_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
});

export const licensePoolSchema = licensePoolBaseSchema
;

export type LicensePoolValid = z.infer<typeof licensePoolSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanLicensePoolValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateLicensePool(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = licensePoolSchema.safeParse(cleanLicensePoolValues(values as Record<string, unknown>));
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
