import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { SERVICE_ORDER_ORDER_TYPE_VALUES, SERVICE_ORDER_CRITICALITY_VALUES } from './types';

export const serviceOrderBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  customerId: z.string({ required_error: 'Customer Id is required' }).uuid('Customer Id must be a UUID'),
  orderType: z.enum(SERVICE_ORDER_ORDER_TYPE_VALUES).optional(),
  requestedOn: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Requested On must be YYYY-MM-DD'),
  targetDate: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Target Date must be YYYY-MM-DD').optional(),
  priority: z.number({ required_error: 'Priority is required', invalid_type_error: 'Priority must be a number' }).int('Priority must be a whole number').min(0, 'Priority must not be negative').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(SERVICE_ORDER_CRITICALITY_VALUES).optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
  notes: z.string().trim().max(4000, 'Notes must be at most 4000 characters').optional(),
});

export const serviceOrderSchema = serviceOrderBaseSchema
;

export type ServiceOrderValid = z.infer<typeof serviceOrderSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanServiceOrderValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateServiceOrder(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = serviceOrderSchema.safeParse(cleanServiceOrderValues(values as Record<string, unknown>));
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
