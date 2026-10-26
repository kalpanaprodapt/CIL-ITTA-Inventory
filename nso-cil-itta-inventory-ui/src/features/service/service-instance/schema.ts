import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { SERVICE_INSTANCE_SERVICE_STATE_VALUES, SERVICE_INSTANCE_LIFECYCLE_STAGE_VALUES, SERVICE_INSTANCE_CRITICALITY_VALUES } from './types';

export const serviceInstanceBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  catalogItemId: z.string({ required_error: 'Catalog Item Id is required' }).uuid('Catalog Item Id must be a UUID'),
  customerId: z.string({ required_error: 'Customer Id is required' }).uuid('Customer Id must be a UUID'),
  activatedOn: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Activated On must be YYYY-MM-DD').optional(),
  bandwidthMbps: z.number({ required_error: 'Bandwidth Mbps is required', invalid_type_error: 'Bandwidth Mbps must be a number' }).int('Bandwidth Mbps must be a whole number').min(0, 'Bandwidth Mbps must not be negative').optional(),
  serviceState: z.enum(SERVICE_INSTANCE_SERVICE_STATE_VALUES).optional(),
  lifecycleStage: z.enum(SERVICE_INSTANCE_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(SERVICE_INSTANCE_CRITICALITY_VALUES).optional(),
});

export const serviceInstanceSchema = serviceInstanceBaseSchema
;

export type ServiceInstanceValid = z.infer<typeof serviceInstanceSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanServiceInstanceValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateServiceInstance(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = serviceInstanceSchema.safeParse(cleanServiceInstanceValues(values as Record<string, unknown>));
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
