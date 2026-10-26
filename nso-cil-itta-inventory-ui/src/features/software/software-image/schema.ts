import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { SOFTWARE_IMAGE_LIFECYCLE_STAGE_VALUES, SOFTWARE_IMAGE_CRITICALITY_VALUES } from './types';

export const softwareImageBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  vendorId: z.string({ required_error: 'Vendor Id is required' }).uuid('Vendor Id must be a UUID'),
  imageName: z.string({ required_error: 'Image Name is required' }).trim().min(1, 'Image Name is required').max(255, 'Image Name must be at most 255 characters'),
  sizeMb: z.number({ required_error: 'Size Mb is required', invalid_type_error: 'Size Mb must be a number' }).int('Size Mb must be a whole number').min(0, 'Size Mb must not be negative').optional(),
  checksum: z.string().trim().max(255, 'Checksum must be at most 255 characters').optional(),
  platform: z.string().trim().max(255, 'Platform must be at most 255 characters').optional(),
  storagePath: z.string().trim().max(255, 'Storage Path must be at most 255 characters').optional(),
  lifecycleStage: z.enum(SOFTWARE_IMAGE_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(SOFTWARE_IMAGE_CRITICALITY_VALUES).optional(),
});

export const softwareImageSchema = softwareImageBaseSchema
;

export type SoftwareImageValid = z.infer<typeof softwareImageSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanSoftwareImageValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateSoftwareImage(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = softwareImageSchema.safeParse(cleanSoftwareImageValues(values as Record<string, unknown>));
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
