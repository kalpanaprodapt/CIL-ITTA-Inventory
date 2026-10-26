import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { CHANGE_REQUEST_CHANGE_TYPE_VALUES, CHANGE_REQUEST_RISK_LEVEL_VALUES, CHANGE_REQUEST_LIFECYCLE_STAGE_VALUES } from './types';

export const changeRequestBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  changeNumber: z.string({ required_error: 'Change Number is required' }).trim().min(1, 'Change Number is required').max(255, 'Change Number must be at most 255 characters'),
  changeType: z.enum(CHANGE_REQUEST_CHANGE_TYPE_VALUES).optional(),
  riskLevel: z.enum(CHANGE_REQUEST_RISK_LEVEL_VALUES).optional(),
  plannedStart: z.string().regex(/^\d{4}-\d{2}-\d{2}T/, 'Planned Start must be an ISO date-time').optional(),
  plannedEnd: z.string().regex(/^\d{4}-\d{2}-\d{2}T/, 'Planned End must be an ISO date-time').optional(),
  notes: z.string().trim().max(4000, 'Notes must be at most 4000 characters').optional(),
  effectiveFrom: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective From must be YYYY-MM-DD').optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(CHANGE_REQUEST_LIFECYCLE_STAGE_VALUES).optional(),
});

export const changeRequestSchema = changeRequestBaseSchema
  .refine((v) => !v.effectiveFrom || !v.effectiveTo || v.effectiveTo >= v.effectiveFrom, {
    path: ['effectiveTo'],
    message: 'effectiveTo must not be earlier than effectiveFrom',
  })
  .refine((v) => !v.plannedStart || !v.plannedEnd || v.plannedEnd >= v.plannedStart, {
    path: ['plannedEnd'],
    message: 'plannedEnd must not be earlier than plannedStart',
  });

export type ChangeRequestValid = z.infer<typeof changeRequestSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanChangeRequestValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateChangeRequest(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = changeRequestSchema.safeParse(cleanChangeRequestValues(values as Record<string, unknown>));
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
