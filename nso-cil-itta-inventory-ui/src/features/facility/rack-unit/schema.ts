import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { RACK_UNIT_FACE_VALUES, RACK_UNIT_CRITICALITY_VALUES } from './types';

export const rackUnitBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  rackId: z.string({ required_error: 'Rack Id is required' }).uuid('Rack Id must be a UUID'),
  unitNumber: z.number({ required_error: 'Unit Number is required', invalid_type_error: 'Unit Number must be a number' }).int('Unit Number must be a whole number').min(0, 'Unit Number must not be negative'),
  occupied: z.boolean().optional(),
  mountedDeviceId: z.string({ required_error: 'Mounted Device Id is required' }).uuid('Mounted Device Id must be a UUID').optional(),
  face: z.enum(RACK_UNIT_FACE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(RACK_UNIT_CRITICALITY_VALUES).optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
});

export const rackUnitSchema = rackUnitBaseSchema
;

export type RackUnitValid = z.infer<typeof rackUnitSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanRackUnitValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateRackUnit(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = rackUnitSchema.safeParse(cleanRackUnitValues(values as Record<string, unknown>));
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
