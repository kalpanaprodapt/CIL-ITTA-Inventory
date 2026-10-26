import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { CABLE_CABLE_TYPE_VALUES, CABLE_CRITICALITY_VALUES } from './types';

export const cableBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  cableType: z.enum(CABLE_CABLE_TYPE_VALUES).optional(),
  lengthM: z.number({ required_error: 'Length M is required', invalid_type_error: 'Length M must be a number' }).min(0, 'Length M must not be negative').optional(),
  aEndPortId: z.string({ required_error: 'A End Port Id is required' }).uuid('A End Port Id must be a UUID').optional(),
  zEndPortId: z.string({ required_error: 'Z End Port Id is required' }).uuid('Z End Port Id must be a UUID').optional(),
  color: z.string().trim().max(255, 'Color must be at most 255 characters').optional(),
  label: z.string().trim().max(255, 'Label must be at most 255 characters').optional(),
  criticality: z.enum(CABLE_CRITICALITY_VALUES).optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
  notes: z.string().trim().max(4000, 'Notes must be at most 4000 characters').optional(),
  effectiveFrom: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective From must be YYYY-MM-DD').optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
});

export const cableSchema = cableBaseSchema
  .refine((v) => !v.effectiveFrom || !v.effectiveTo || v.effectiveTo >= v.effectiveFrom, {
    path: ['effectiveTo'],
    message: 'effectiveTo must not be earlier than effectiveFrom',
  });

export type CableValid = z.infer<typeof cableSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanCableValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateCable(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = cableSchema.safeParse(cleanCableValues(values as Record<string, unknown>));
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
