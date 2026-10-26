import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { ROOM_ROOM_TYPE_VALUES, ROOM_LIFECYCLE_STAGE_VALUES } from './types';

export const roomBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  floorId: z.string({ required_error: 'Floor Id is required' }).uuid('Floor Id must be a UUID'),
  roomType: z.enum(ROOM_ROOM_TYPE_VALUES).optional(),
  areaSqm: z.number({ required_error: 'Area Sqm is required', invalid_type_error: 'Area Sqm must be a number' }).min(0, 'Area Sqm must not be negative').optional(),
  coolingTons: z.number({ required_error: 'Cooling Tons is required', invalid_type_error: 'Cooling Tons must be a number' }).min(0, 'Cooling Tons must not be negative').optional(),
  fireZone: z.string().trim().max(255, 'Fire Zone must be at most 255 characters').optional(),
  effectiveFrom: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective From must be YYYY-MM-DD').optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(ROOM_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
});

export const roomSchema = roomBaseSchema
  .refine((v) => !v.effectiveFrom || !v.effectiveTo || v.effectiveTo >= v.effectiveFrom, {
    path: ['effectiveTo'],
    message: 'effectiveTo must not be earlier than effectiveFrom',
  });

export type RoomValid = z.infer<typeof roomSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanRoomValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateRoom(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = roomSchema.safeParse(cleanRoomValues(values as Record<string, unknown>));
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
