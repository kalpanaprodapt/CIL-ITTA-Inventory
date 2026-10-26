import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { UTILIZATION_SAMPLE_LIFECYCLE_STAGE_VALUES, UTILIZATION_SAMPLE_CRITICALITY_VALUES } from './types';

export const utilizationSampleBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  targetRef: z.string({ required_error: 'Target Ref is required' }).trim().min(1, 'Target Ref is required').max(255, 'Target Ref must be at most 255 characters'),
  metric: z.string({ required_error: 'Metric is required' }).trim().min(1, 'Metric is required').max(255, 'Metric must be at most 255 characters'),
  sampledAt: z.string().regex(/^\d{4}-\d{2}-\d{2}T/, 'Sampled At must be an ISO date-time'),
  valuePct: z.number({ required_error: 'Value Pct is required', invalid_type_error: 'Value Pct must be a number' }).min(0, 'Value Pct must not be negative'),
  peak: z.boolean().optional(),
  lifecycleStage: z.enum(UTILIZATION_SAMPLE_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(UTILIZATION_SAMPLE_CRITICALITY_VALUES).optional(),
});

export const utilizationSampleSchema = utilizationSampleBaseSchema
;

export type UtilizationSampleValid = z.infer<typeof utilizationSampleSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanUtilizationSampleValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateUtilizationSample(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = utilizationSampleSchema.safeParse(cleanUtilizationSampleValues(values as Record<string, unknown>));
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
