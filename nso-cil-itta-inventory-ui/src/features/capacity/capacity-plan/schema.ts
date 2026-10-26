import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { CAPACITY_PLAN_LIFECYCLE_STAGE_VALUES } from './types';

export const capacityPlanBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  planName: z.string({ required_error: 'Plan Name is required' }).trim().min(1, 'Plan Name is required').max(255, 'Plan Name must be at most 255 characters'),
  horizonMonths: z.number({ required_error: 'Horizon Months is required', invalid_type_error: 'Horizon Months must be a number' }).int('Horizon Months must be a whole number').min(0, 'Horizon Months must not be negative'),
  targetUtilizationPct: z.number({ required_error: 'Target Utilization Pct is required', invalid_type_error: 'Target Utilization Pct must be a number' }).min(0, 'Target Utilization Pct must not be negative').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  approvedOn: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Approved On must be YYYY-MM-DD').optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(CAPACITY_PLAN_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
});

export const capacityPlanSchema = capacityPlanBaseSchema
;

export type CapacityPlanValid = z.infer<typeof capacityPlanSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanCapacityPlanValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateCapacityPlan(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = capacityPlanSchema.safeParse(cleanCapacityPlanValues(values as Record<string, unknown>));
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
