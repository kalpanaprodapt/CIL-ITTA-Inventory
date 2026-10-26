import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { ROUTE_TARGET_RT_TYPE_VALUES, ROUTE_TARGET_LIFECYCLE_STAGE_VALUES, ROUTE_TARGET_CRITICALITY_VALUES } from './types';

export const routeTargetBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  asn: z.number({ required_error: 'Asn is required', invalid_type_error: 'Asn must be a number' }).int('Asn must be a whole number').min(0, 'Asn must not be negative'),
  assignedNumber: z.number({ required_error: 'Assigned Number is required', invalid_type_error: 'Assigned Number must be a number' }).int('Assigned Number must be a whole number').min(0, 'Assigned Number must not be negative'),
  rtType: z.enum(ROUTE_TARGET_RT_TYPE_VALUES).optional(),
  vrfId: z.string({ required_error: 'Vrf Id is required' }).uuid('Vrf Id must be a UUID').optional(),
  lifecycleStage: z.enum(ROUTE_TARGET_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(ROUTE_TARGET_CRITICALITY_VALUES).optional(),
});

export const routeTargetSchema = routeTargetBaseSchema
;

export type RouteTargetValid = z.infer<typeof routeTargetSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanRouteTargetValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateRouteTarget(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = routeTargetSchema.safeParse(cleanRouteTargetValues(values as Record<string, unknown>));
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
