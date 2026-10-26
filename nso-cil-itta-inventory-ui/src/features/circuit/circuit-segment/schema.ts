import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { CIRCUIT_SEGMENT_CRITICALITY_VALUES } from './types';

export const circuitSegmentBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  circuitId: z.string({ required_error: 'Circuit Id is required' }).uuid('Circuit Id must be a UUID'),
  segmentOrder: z.number({ required_error: 'Segment Order is required', invalid_type_error: 'Segment Order must be a number' }).int('Segment Order must be a whole number').min(0, 'Segment Order must not be negative'),
  aTerminationId: z.string({ required_error: 'A Termination Id is required' }).uuid('A Termination Id must be a UUID').optional(),
  zTerminationId: z.string({ required_error: 'Z Termination Id is required' }).uuid('Z Termination Id must be a UUID').optional(),
  latencyMs: z.number({ required_error: 'Latency Ms is required', invalid_type_error: 'Latency Ms must be a number' }).min(0, 'Latency Ms must not be negative').optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(CIRCUIT_SEGMENT_CRITICALITY_VALUES).optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
});

export const circuitSegmentSchema = circuitSegmentBaseSchema
;

export type CircuitSegmentValid = z.infer<typeof circuitSegmentSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanCircuitSegmentValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateCircuitSegment(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = circuitSegmentSchema.safeParse(cleanCircuitSegmentValues(values as Record<string, unknown>));
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
