import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { BASE_STATION_TECHNOLOGY_VALUES, BASE_STATION_LIFECYCLE_STAGE_VALUES } from './types';

export const baseStationBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  siteId: z.string({ required_error: 'Site Id is required' }).uuid('Site Id must be a UUID'),
  enbId: z.number({ required_error: 'Enb Id is required', invalid_type_error: 'Enb Id must be a number' }).int('Enb Id must be a whole number').min(0, 'Enb Id must not be negative'),
  technology: z.enum(BASE_STATION_TECHNOLOGY_VALUES).optional(),
  sectorCount: z.number({ required_error: 'Sector Count is required', invalid_type_error: 'Sector Count must be a number' }).int('Sector Count must be a whole number').min(0, 'Sector Count must not be negative').optional(),
  cellCount: z.number({ required_error: 'Cell Count is required', invalid_type_error: 'Cell Count must be a number' }).int('Cell Count must be a whole number').min(0, 'Cell Count must not be negative').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(BASE_STATION_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
});

export const baseStationSchema = baseStationBaseSchema
;

export type BaseStationValid = z.infer<typeof baseStationSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanBaseStationValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateBaseStation(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = baseStationSchema.safeParse(cleanBaseStationValues(values as Record<string, unknown>));
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
