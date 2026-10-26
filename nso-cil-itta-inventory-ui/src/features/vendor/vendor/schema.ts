import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { VENDOR_TIER_VALUES, VENDOR_LIFECYCLE_STAGE_VALUES } from './types';

export const vendorBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  legalName: z.string({ required_error: 'Legal Name is required' }).trim().min(1, 'Legal Name is required').max(255, 'Legal Name must be at most 255 characters'),
  supportEmail: z.string().trim().max(255, 'Support Email must be at most 255 characters').optional(),
  supportPhone: z.string().trim().max(255, 'Support Phone must be at most 255 characters').optional(),
  website: z.string().trim().max(255, 'Website must be at most 255 characters').optional(),
  tier: z.enum(VENDOR_TIER_VALUES).optional(),
  effectiveFrom: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective From must be YYYY-MM-DD').optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(VENDOR_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
});

export const vendorSchema = vendorBaseSchema
  .refine((v) => !v.effectiveFrom || !v.effectiveTo || v.effectiveTo >= v.effectiveFrom, {
    path: ['effectiveTo'],
    message: 'effectiveTo must not be earlier than effectiveFrom',
  });

export type VendorValid = z.infer<typeof vendorSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanVendorValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateVendor(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = vendorSchema.safeParse(cleanVendorValues(values as Record<string, unknown>));
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
