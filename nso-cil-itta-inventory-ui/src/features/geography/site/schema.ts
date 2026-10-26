import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { SITE_SITE_TYPE_VALUES, SITE_LIFECYCLE_STAGE_VALUES } from './types';

export const siteBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  cityId: z.string({ required_error: 'City Id is required' }).uuid('City Id must be a UUID'),
  siteType: z.enum(SITE_SITE_TYPE_VALUES).optional(),
  address: z.string().trim().max(255, 'Address must be at most 255 characters').optional(),
  latitude: z.number({ required_error: 'Latitude is required', invalid_type_error: 'Latitude must be a number' }).optional(),
  longitude: z.number({ required_error: 'Longitude is required', invalid_type_error: 'Longitude must be a number' }).optional(),
  powerKw: z.number({ required_error: 'Power Kw is required', invalid_type_error: 'Power Kw must be a number' }).min(0, 'Power Kw must not be negative').optional(),
  accessHours: z.string().trim().max(255, 'Access Hours must be at most 255 characters').optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(SITE_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
});

export const siteSchema = siteBaseSchema
;

export type SiteValid = z.infer<typeof siteSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanSiteValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateSite(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = siteSchema.safeParse(cleanSiteValues(values as Record<string, unknown>));
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
