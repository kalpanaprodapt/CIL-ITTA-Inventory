import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { PATH_TRACE_LIFECYCLE_STAGE_VALUES, PATH_TRACE_CRITICALITY_VALUES } from './types';

export const pathTraceBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  sourceRef: z.string({ required_error: 'Source Ref is required' }).trim().min(1, 'Source Ref is required').max(255, 'Source Ref must be at most 255 characters'),
  targetRef: z.string({ required_error: 'Target Ref is required' }).trim().min(1, 'Target Ref is required').max(255, 'Target Ref must be at most 255 characters'),
  hopCount: z.number({ required_error: 'Hop Count is required', invalid_type_error: 'Hop Count must be a number' }).int('Hop Count must be a whole number').min(0, 'Hop Count must not be negative').optional(),
  totalLatencyMs: z.number({ required_error: 'Total Latency Ms is required', invalid_type_error: 'Total Latency Ms must be a number' }).min(0, 'Total Latency Ms must not be negative').optional(),
  traceMethod: z.string().trim().max(255, 'Trace Method must be at most 255 characters').optional(),
  tracedAt: z.string().regex(/^\d{4}-\d{2}-\d{2}T/, 'Traced At must be an ISO date-time').optional(),
  lifecycleStage: z.enum(PATH_TRACE_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(PATH_TRACE_CRITICALITY_VALUES).optional(),
});

export const pathTraceSchema = pathTraceBaseSchema
;

export type PathTraceValid = z.infer<typeof pathTraceSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanPathTraceValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validatePathTrace(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = pathTraceSchema.safeParse(cleanPathTraceValues(values as Record<string, unknown>));
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
