import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { RACK_LIFECYCLE_STAGE_VALUES } from './types';

export const rackBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  roomId: z.string({ required_error: 'Room Id is required' }).uuid('Room Id must be a UUID'),
  rackUnits: z.number({ required_error: 'Rack Units is required', invalid_type_error: 'Rack Units must be a number' }).int('Rack Units must be a whole number').min(0, 'Rack Units must not be negative'),
  widthMm: z.number({ required_error: 'Width Mm is required', invalid_type_error: 'Width Mm must be a number' }).int('Width Mm must be a whole number').min(0, 'Width Mm must not be negative').optional(),
  depthMm: z.number({ required_error: 'Depth Mm is required', invalid_type_error: 'Depth Mm must be a number' }).int('Depth Mm must be a whole number').min(0, 'Depth Mm must not be negative').optional(),
  maxWeightKg: z.number({ required_error: 'Max Weight Kg is required', invalid_type_error: 'Max Weight Kg must be a number' }).min(0, 'Max Weight Kg must not be negative').optional(),
  rowLabel: z.string().trim().max(255, 'Row Label must be at most 255 characters').optional(),
  position: z.string().trim().max(255, 'Position must be at most 255 characters').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(RACK_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
});

export const rackSchema = rackBaseSchema
;

export type RackValid = z.infer<typeof rackSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanRackValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateRack(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = rackSchema.safeParse(cleanRackValues(values as Record<string, unknown>));
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
