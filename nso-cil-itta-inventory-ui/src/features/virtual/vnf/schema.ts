import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { VNF_LIFECYCLE_STAGE_VALUES, VNF_CRITICALITY_VALUES } from './types';

export const vnfBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  vnfName: z.string({ required_error: 'Vnf Name is required' }).trim().min(1, 'Vnf Name is required').max(255, 'Vnf Name must be at most 255 characters'),
  vnfType: z.string({ required_error: 'Vnf Type is required' }).trim().min(1, 'Vnf Type is required').max(255, 'Vnf Type must be at most 255 characters'),
  flavor: z.string().trim().max(255, 'Flavor must be at most 255 characters').optional(),
  vcpu: z.number({ required_error: 'Vcpu is required', invalid_type_error: 'Vcpu must be a number' }).int('Vcpu must be a whole number').min(0, 'Vcpu must not be negative').optional(),
  memoryGb: z.number({ required_error: 'Memory Gb is required', invalid_type_error: 'Memory Gb must be a number' }).int('Memory Gb must be a whole number').min(0, 'Memory Gb must not be negative').optional(),
  instanceCount: z.number({ required_error: 'Instance Count is required', invalid_type_error: 'Instance Count must be a number' }).int('Instance Count must be a whole number').min(0, 'Instance Count must not be negative').optional(),
  lifecycleStage: z.enum(VNF_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(VNF_CRITICALITY_VALUES).optional(),
});

export const vnfSchema = vnfBaseSchema
;

export type VnfValid = z.infer<typeof vnfSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanVnfValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateVnf(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = vnfSchema.safeParse(cleanVnfValues(values as Record<string, unknown>));
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
