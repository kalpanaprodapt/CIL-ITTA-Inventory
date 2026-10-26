import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { VRF_INSTANCE_LIFECYCLE_STAGE_VALUES } from './types';

export const vrfInstanceBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  vrfName: z.string({ required_error: 'Vrf Name is required' }).trim().min(1, 'Vrf Name is required').max(255, 'Vrf Name must be at most 255 characters'),
  routeDistinguisher: z.string({ required_error: 'Route Distinguisher is required' }).trim().min(1, 'Route Distinguisher is required').max(255, 'Route Distinguisher must be at most 255 characters'),
  importTargets: z.string().trim().max(255, 'Import Targets must be at most 255 characters').optional(),
  exportTargets: z.string().trim().max(255, 'Export Targets must be at most 255 characters').optional(),
  deviceId: z.string({ required_error: 'Device Id is required' }).uuid('Device Id must be a UUID').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(VRF_INSTANCE_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
});

export const vrfInstanceSchema = vrfInstanceBaseSchema
;

export type VrfInstanceValid = z.infer<typeof vrfInstanceSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanVrfInstanceValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateVrfInstance(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = vrfInstanceSchema.safeParse(cleanVrfInstanceValues(values as Record<string, unknown>));
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
