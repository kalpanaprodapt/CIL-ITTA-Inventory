import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';


export const pseudoWireBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  vcId: z.number({ required_error: 'Vc Id is required', invalid_type_error: 'Vc Id must be a number' }).int('Vc Id must be a whole number').min(0, 'Vc Id must not be negative'),
  aEndId: z.string({ required_error: 'A End Id is required' }).uuid('A End Id must be a UUID'),
  zEndId: z.string({ required_error: 'Z End Id is required' }).uuid('Z End Id must be a UUID'),
  encapsulation: z.string().trim().max(255, 'Encapsulation must be at most 255 characters').optional(),
  controlWord: z.boolean().optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
  notes: z.string().trim().max(4000, 'Notes must be at most 4000 characters').optional(),
  effectiveFrom: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective From must be YYYY-MM-DD').optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
});

export const pseudoWireSchema = pseudoWireBaseSchema
  .refine((v) => !v.effectiveFrom || !v.effectiveTo || v.effectiveTo >= v.effectiveFrom, {
    path: ['effectiveTo'],
    message: 'effectiveTo must not be earlier than effectiveFrom',
  });

export type PseudoWireValid = z.infer<typeof pseudoWireSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanPseudoWireValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validatePseudoWire(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = pseudoWireSchema.safeParse(cleanPseudoWireValues(values as Record<string, unknown>));
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
