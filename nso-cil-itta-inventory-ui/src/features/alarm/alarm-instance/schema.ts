import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { ALARM_INSTANCE_ALARM_STATE_VALUES, ALARM_INSTANCE_LIFECYCLE_STAGE_VALUES } from './types';

export const alarmInstanceBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  definitionId: z.string({ required_error: 'Definition Id is required' }).uuid('Definition Id must be a UUID'),
  sourceRef: z.string({ required_error: 'Source Ref is required' }).trim().min(1, 'Source Ref is required').max(255, 'Source Ref must be at most 255 characters'),
  raisedAt: z.string().regex(/^\d{4}-\d{2}-\d{2}T/, 'Raised At must be an ISO date-time'),
  clearedAt: z.string().regex(/^\d{4}-\d{2}-\d{2}T/, 'Cleared At must be an ISO date-time').optional(),
  ackedBy: z.string().trim().max(255, 'Acked By must be at most 255 characters').optional(),
  alarmState: z.enum(ALARM_INSTANCE_ALARM_STATE_VALUES).optional(),
  effectiveFrom: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective From must be YYYY-MM-DD').optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(ALARM_INSTANCE_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
});

export const alarmInstanceSchema = alarmInstanceBaseSchema
  .refine((v) => !v.effectiveFrom || !v.effectiveTo || v.effectiveTo >= v.effectiveFrom, {
    path: ['effectiveTo'],
    message: 'effectiveTo must not be earlier than effectiveFrom',
  })
  .refine((v) => !v.raisedAt || !v.clearedAt || v.clearedAt >= v.raisedAt, {
    path: ['clearedAt'],
    message: 'clearedAt must not be earlier than raisedAt',
  });

export type AlarmInstanceValid = z.infer<typeof alarmInstanceSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanAlarmInstanceValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateAlarmInstance(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = alarmInstanceSchema.safeParse(cleanAlarmInstanceValues(values as Record<string, unknown>));
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
