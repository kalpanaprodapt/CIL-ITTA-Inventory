import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { SOFTWARE_VERSION_RELEASE_TRAIN_VALUES, SOFTWARE_VERSION_CRITICALITY_VALUES } from './types';

export const softwareVersionBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  imageId: z.string({ required_error: 'Image Id is required' }).uuid('Image Id must be a UUID'),
  versionString: z.string({ required_error: 'Version String is required' }).trim().min(1, 'Version String is required').max(255, 'Version String must be at most 255 characters'),
  releaseDate: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Release Date must be YYYY-MM-DD').optional(),
  endOfSupport: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'End Of Support must be YYYY-MM-DD').optional(),
  releaseTrain: z.enum(SOFTWARE_VERSION_RELEASE_TRAIN_VALUES).optional(),
  cveCount: z.number({ required_error: 'Cve Count is required', invalid_type_error: 'Cve Count must be a number' }).int('Cve Count must be a whole number').min(0, 'Cve Count must not be negative').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(SOFTWARE_VERSION_CRITICALITY_VALUES).optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
  notes: z.string().trim().max(4000, 'Notes must be at most 4000 characters').optional(),
});

export const softwareVersionSchema = softwareVersionBaseSchema
;

export type SoftwareVersionValid = z.infer<typeof softwareVersionSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanSoftwareVersionValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateSoftwareVersion(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = softwareVersionSchema.safeParse(cleanSoftwareVersionValues(values as Record<string, unknown>));
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
