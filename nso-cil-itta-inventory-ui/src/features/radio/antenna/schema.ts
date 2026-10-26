import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { ANTENNA_CRITICALITY_VALUES } from './types';

export const antennaBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  sectorId: z.string({ required_error: 'Sector Id is required' }).uuid('Sector Id must be a UUID'),
  model: z.string().trim().max(255, 'Model must be at most 255 characters').optional(),
  heightM: z.number({ required_error: 'Height M is required', invalid_type_error: 'Height M must be a number' }).min(0, 'Height M must not be negative').optional(),
  azimuthDeg: z.number({ required_error: 'Azimuth Deg is required', invalid_type_error: 'Azimuth Deg must be a number' }).optional(),
  tiltDeg: z.number({ required_error: 'Tilt Deg is required', invalid_type_error: 'Tilt Deg must be a number' }).optional(),
  gainDbi: z.number({ required_error: 'Gain Dbi is required', invalid_type_error: 'Gain Dbi must be a number' }).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(ANTENNA_CRITICALITY_VALUES).optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
});

export const antennaSchema = antennaBaseSchema
;

export type AntennaValid = z.infer<typeof antennaSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanAntennaValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateAntenna(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = antennaSchema.safeParse(cleanAntennaValues(values as Record<string, unknown>));
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
