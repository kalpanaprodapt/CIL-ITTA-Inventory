import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { HARDWARE_MODEL_LIFECYCLE_STAGE_VALUES } from './types';

export const hardwareModelBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  vendorId: z.string({ required_error: 'Vendor Id is required' }).uuid('Vendor Id must be a UUID'),
  modelNumber: z.string({ required_error: 'Model Number is required' }).trim().min(1, 'Model Number is required').max(255, 'Model Number must be at most 255 characters'),
  formFactor: z.string().trim().max(255, 'Form Factor must be at most 255 characters').optional(),
  powerWatts: z.number({ required_error: 'Power Watts is required', invalid_type_error: 'Power Watts must be a number' }).int('Power Watts must be a whole number').min(0, 'Power Watts must not be negative').optional(),
  eolDate: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Eol Date must be YYYY-MM-DD').optional(),
  eosDate: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Eos Date must be YYYY-MM-DD').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(HARDWARE_MODEL_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
});

export const hardwareModelSchema = hardwareModelBaseSchema
;

export type HardwareModelValid = z.infer<typeof hardwareModelSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanHardwareModelValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateHardwareModel(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = hardwareModelSchema.safeParse(cleanHardwareModelValues(values as Record<string, unknown>));
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
