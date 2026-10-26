import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { CIRCUIT_PROVIDER_PROVIDER_TYPE_VALUES, CIRCUIT_PROVIDER_CRITICALITY_VALUES } from './types';

export const circuitProviderBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  providerName: z.string({ required_error: 'Provider Name is required' }).trim().min(1, 'Provider Name is required').max(255, 'Provider Name must be at most 255 characters'),
  accountNumber: z.string().trim().max(255, 'Account Number must be at most 255 characters').optional(),
  noc: z.string().trim().max(255, 'Noc must be at most 255 characters').optional(),
  escalationEmail: z.string().trim().max(255, 'Escalation Email must be at most 255 characters').optional(),
  providerType: z.enum(CIRCUIT_PROVIDER_PROVIDER_TYPE_VALUES).optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(CIRCUIT_PROVIDER_CRITICALITY_VALUES).optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
  notes: z.string().trim().max(4000, 'Notes must be at most 4000 characters').optional(),
  effectiveFrom: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective From must be YYYY-MM-DD').optional(),
});

export const circuitProviderSchema = circuitProviderBaseSchema
;

export type CircuitProviderValid = z.infer<typeof circuitProviderSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanCircuitProviderValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateCircuitProvider(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = circuitProviderSchema.safeParse(cleanCircuitProviderValues(values as Record<string, unknown>));
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
