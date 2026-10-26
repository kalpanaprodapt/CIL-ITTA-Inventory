import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { COUNTRY_CRITICALITY_VALUES } from './types';

export const countryBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  isoCode: z.string({ required_error: 'Iso Code is required' }).trim().min(1, 'Iso Code is required').max(255, 'Iso Code must be at most 255 characters'),
  isoNumeric: z.number({ required_error: 'Iso Numeric is required', invalid_type_error: 'Iso Numeric must be a number' }).int('Iso Numeric must be a whole number').min(0, 'Iso Numeric must not be negative').optional(),
  dialCode: z.string().trim().max(255, 'Dial Code must be at most 255 characters').optional(),
  currency: z.string().trim().max(255, 'Currency must be at most 255 characters').optional(),
  regulatoryBody: z.string().trim().max(255, 'Regulatory Body must be at most 255 characters').optional(),
  timezone: z.string().trim().max(255, 'Timezone must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(COUNTRY_CRITICALITY_VALUES).optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
  notes: z.string().trim().max(4000, 'Notes must be at most 4000 characters').optional(),
});

export const countrySchema = countryBaseSchema
;

export type CountryValid = z.infer<typeof countrySchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanCountryValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateCountry(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = countrySchema.safeParse(cleanCountryValues(values as Record<string, unknown>));
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
