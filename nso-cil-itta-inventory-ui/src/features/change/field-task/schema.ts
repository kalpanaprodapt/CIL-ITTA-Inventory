import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { FIELD_TASK_CRITICALITY_VALUES } from './types';

export const fieldTaskBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  workOrderId: z.string({ required_error: 'Work Order Id is required' }).uuid('Work Order Id must be a UUID'),
  taskName: z.string({ required_error: 'Task Name is required' }).trim().min(1, 'Task Name is required').max(255, 'Task Name must be at most 255 characters'),
  sequence: z.number({ required_error: 'Sequence is required', invalid_type_error: 'Sequence must be a number' }).int('Sequence must be a whole number').min(0, 'Sequence must not be negative'),
  durationMinutes: z.number({ required_error: 'Duration Minutes is required', invalid_type_error: 'Duration Minutes must be a number' }).int('Duration Minutes must be a whole number').min(0, 'Duration Minutes must not be negative').optional(),
  completedOn: z.string().regex(/^\d{4}-\d{2}-\d{2}T/, 'Completed On must be an ISO date-time').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(FIELD_TASK_CRITICALITY_VALUES).optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
  notes: z.string().trim().max(4000, 'Notes must be at most 4000 characters').optional(),
});

export const fieldTaskSchema = fieldTaskBaseSchema
;

export type FieldTaskValid = z.infer<typeof fieldTaskSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanFieldTaskValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateFieldTask(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = fieldTaskSchema.safeParse(cleanFieldTaskValues(values as Record<string, unknown>));
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
