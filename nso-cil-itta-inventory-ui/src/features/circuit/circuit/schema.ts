import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { CIRCUIT_CIRCUIT_TYPE_VALUES, CIRCUIT_LIFECYCLE_STAGE_VALUES } from './types';

export const circuitBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  circuitId: z.string({ required_error: 'Circuit Id is required' }).trim().min(1, 'Circuit Id is required').max(255, 'Circuit Id must be at most 255 characters'),
  circuitType: z.enum(CIRCUIT_CIRCUIT_TYPE_VALUES).optional(),
  bandwidthMbps: z.number({ required_error: 'Bandwidth Mbps is required', invalid_type_error: 'Bandwidth Mbps must be a number' }).int('Bandwidth Mbps must be a whole number').min(0, 'Bandwidth Mbps must not be negative'),
  providerId: z.string({ required_error: 'Provider Id is required' }).uuid('Provider Id must be a UUID').optional(),
  aSiteId: z.string({ required_error: 'A Site Id is required' }).uuid('A Site Id must be a UUID').optional(),
  zSiteId: z.string({ required_error: 'Z Site Id is required' }).uuid('Z Site Id must be a UUID').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(CIRCUIT_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
});

export const circuitSchema = circuitBaseSchema
;

export type CircuitValid = z.infer<typeof circuitSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanCircuitValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateCircuit(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = circuitSchema.safeParse(cleanCircuitValues(values as Record<string, unknown>));
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
