import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { CUSTOMER_CONTACT_CONTACT_ROLE_VALUES, CUSTOMER_CONTACT_LIFECYCLE_STAGE_VALUES } from './types';

export const customerContactBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  customerId: z.string({ required_error: 'Customer Id is required' }).uuid('Customer Id must be a UUID'),
  fullName: z.string({ required_error: 'Full Name is required' }).trim().min(1, 'Full Name is required').max(255, 'Full Name must be at most 255 characters'),
  email: z.string().trim().max(255, 'Email must be at most 255 characters').optional(),
  phone: z.string().trim().max(255, 'Phone must be at most 255 characters').optional(),
  contactRole: z.enum(CUSTOMER_CONTACT_CONTACT_ROLE_VALUES).optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(CUSTOMER_CONTACT_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
});

export const customerContactSchema = customerContactBaseSchema
;

export type CustomerContactValid = z.infer<typeof customerContactSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanCustomerContactValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateCustomerContact(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = customerContactSchema.safeParse(cleanCustomerContactValues(values as Record<string, unknown>));
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
