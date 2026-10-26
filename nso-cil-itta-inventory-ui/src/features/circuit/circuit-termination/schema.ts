import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { CIRCUIT_TERMINATION_SIDE_VALUES } from './types';

export const circuitTerminationBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  circuitId: z.string({ required_error: 'Circuit Id is required' }).uuid('Circuit Id must be a UUID'),
  side: z.enum(CIRCUIT_TERMINATION_SIDE_VALUES).optional(),
  portId: z.string({ required_error: 'Port Id is required' }).uuid('Port Id must be a UUID'),
  xconnectId: z.string().trim().max(255, 'Xconnect Id must be at most 255 characters').optional(),
  patchInfo: z.string().trim().max(255, 'Patch Info must be at most 255 characters').optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
  notes: z.string().trim().max(4000, 'Notes must be at most 4000 characters').optional(),
  effectiveFrom: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective From must be YYYY-MM-DD').optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
});

export const circuitTerminationSchema = circuitTerminationBaseSchema
  .refine((v) => !v.effectiveFrom || !v.effectiveTo || v.effectiveTo >= v.effectiveFrom, {
    path: ['effectiveTo'],
    message: 'effectiveTo must not be earlier than effectiveFrom',
  });

export type CircuitTerminationValid = z.infer<typeof circuitTerminationSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanCircuitTerminationValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateCircuitTermination(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = circuitTerminationSchema.safeParse(cleanCircuitTerminationValues(values as Record<string, unknown>));
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
