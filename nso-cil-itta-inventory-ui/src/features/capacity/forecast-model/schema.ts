import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { FORECAST_MODEL_ALGORITHM_VALUES, FORECAST_MODEL_CRITICALITY_VALUES } from './types';

export const forecastModelBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  modelName: z.string({ required_error: 'Model Name is required' }).trim().min(1, 'Model Name is required').max(255, 'Model Name must be at most 255 characters'),
  algorithm: z.enum(FORECAST_MODEL_ALGORITHM_VALUES).optional(),
  trainedOn: z.string().regex(/^\d{4}-\d{2}-\d{2}T/, 'Trained On must be an ISO date-time').optional(),
  mape: z.number({ required_error: 'Mape is required', invalid_type_error: 'Mape must be a number' }).min(0, 'Mape must not be negative').optional(),
  criticality: z.enum(FORECAST_MODEL_CRITICALITY_VALUES).optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
  notes: z.string().trim().max(4000, 'Notes must be at most 4000 characters').optional(),
  effectiveFrom: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective From must be YYYY-MM-DD').optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
});

export const forecastModelSchema = forecastModelBaseSchema
  .refine((v) => !v.effectiveFrom || !v.effectiveTo || v.effectiveTo >= v.effectiveFrom, {
    path: ['effectiveTo'],
    message: 'effectiveTo must not be earlier than effectiveFrom',
  });

export type ForecastModelValid = z.infer<typeof forecastModelSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanForecastModelValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateForecastModel(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = forecastModelSchema.safeParse(cleanForecastModelValues(values as Record<string, unknown>));
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
