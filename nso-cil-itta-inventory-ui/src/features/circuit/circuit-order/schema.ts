import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { CIRCUIT_ORDER_ORDER_STATUS_VALUES, CIRCUIT_ORDER_LIFECYCLE_STAGE_VALUES } from './types';

export const circuitOrderBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  circuitId: z.string({ required_error: 'Circuit Id is required' }).uuid('Circuit Id must be a UUID').optional(),
  orderNumber: z.string({ required_error: 'Order Number is required' }).trim().min(1, 'Order Number is required').max(255, 'Order Number must be at most 255 characters'),
  orderedOn: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Ordered On must be YYYY-MM-DD'),
  requestedDate: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Requested Date must be YYYY-MM-DD').optional(),
  committedDate: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Committed Date must be YYYY-MM-DD').optional(),
  orderStatus: z.enum(CIRCUIT_ORDER_ORDER_STATUS_VALUES).optional(),
  effectiveFrom: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective From must be YYYY-MM-DD').optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(CIRCUIT_ORDER_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
});

export const circuitOrderSchema = circuitOrderBaseSchema
  .refine((v) => !v.effectiveFrom || !v.effectiveTo || v.effectiveTo >= v.effectiveFrom, {
    path: ['effectiveTo'],
    message: 'effectiveTo must not be earlier than effectiveFrom',
  });

export type CircuitOrderValid = z.infer<typeof circuitOrderSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanCircuitOrderValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateCircuitOrder(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = circuitOrderSchema.safeParse(cleanCircuitOrderValues(values as Record<string, unknown>));
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
