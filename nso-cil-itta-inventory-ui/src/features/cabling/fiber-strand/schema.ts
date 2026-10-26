import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { FIBER_STRAND_CRITICALITY_VALUES } from './types';

export const fiberStrandBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  cableId: z.string({ required_error: 'Cable Id is required' }).uuid('Cable Id must be a UUID'),
  strandNumber: z.number({ required_error: 'Strand Number is required', invalid_type_error: 'Strand Number must be a number' }).int('Strand Number must be a whole number').min(0, 'Strand Number must not be negative'),
  bufferTube: z.string().trim().max(255, 'Buffer Tube must be at most 255 characters').optional(),
  tubeColor: z.string().trim().max(255, 'Tube Color must be at most 255 characters').optional(),
  attenuationDb: z.number({ required_error: 'Attenuation Db is required', invalid_type_error: 'Attenuation Db must be a number' }).optional(),
  inUse: z.boolean().optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(FIBER_STRAND_CRITICALITY_VALUES).optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
  notes: z.string().trim().max(4000, 'Notes must be at most 4000 characters').optional(),
});

export const fiberStrandSchema = fiberStrandBaseSchema
;

export type FiberStrandValid = z.infer<typeof fiberStrandSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanFiberStrandValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateFiberStrand(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = fiberStrandSchema.safeParse(cleanFiberStrandValues(values as Record<string, unknown>));
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
