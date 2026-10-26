import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { WORK_ORDER_WORK_STATE_VALUES, WORK_ORDER_LIFECYCLE_STAGE_VALUES, WORK_ORDER_CRITICALITY_VALUES } from './types';

export const workOrderBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  workOrderNumber: z.string({ required_error: 'Work Order Number is required' }).trim().min(1, 'Work Order Number is required').max(255, 'Work Order Number must be at most 255 characters'),
  workType: z.string({ required_error: 'Work Type is required' }).trim().min(1, 'Work Type is required').max(255, 'Work Type must be at most 255 characters'),
  assignedTechnicianId: z.string({ required_error: 'Assigned Technician Id is required' }).uuid('Assigned Technician Id must be a UUID').optional(),
  dueDate: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Due Date must be YYYY-MM-DD').optional(),
  workState: z.enum(WORK_ORDER_WORK_STATE_VALUES).optional(),
  lifecycleStage: z.enum(WORK_ORDER_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(WORK_ORDER_CRITICALITY_VALUES).optional(),
});

export const workOrderSchema = workOrderBaseSchema
;

export type WorkOrderValid = z.infer<typeof workOrderSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanWorkOrderValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateWorkOrder(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = workOrderSchema.safeParse(cleanWorkOrderValues(values as Record<string, unknown>));
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
