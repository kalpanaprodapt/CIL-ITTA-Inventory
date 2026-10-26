import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { AMPLIFIER_AMP_TYPE_VALUES, AMPLIFIER_LIFECYCLE_STAGE_VALUES, AMPLIFIER_CRITICALITY_VALUES } from './types';

export const amplifierBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  systemId: z.string({ required_error: 'System Id is required' }).uuid('System Id must be a UUID'),
  ampType: z.enum(AMPLIFIER_AMP_TYPE_VALUES).optional(),
  gainDb: z.number({ required_error: 'Gain Db is required', invalid_type_error: 'Gain Db must be a number' }),
  outputPowerDbm: z.number({ required_error: 'Output Power Dbm is required', invalid_type_error: 'Output Power Dbm must be a number' }).optional(),
  noiseFigureDb: z.number({ required_error: 'Noise Figure Db is required', invalid_type_error: 'Noise Figure Db must be a number' }).optional(),
  lifecycleStage: z.enum(AMPLIFIER_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(AMPLIFIER_CRITICALITY_VALUES).optional(),
});

export const amplifierSchema = amplifierBaseSchema
;

export type AmplifierValid = z.infer<typeof amplifierSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanAmplifierValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateAmplifier(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = amplifierSchema.safeParse(cleanAmplifierValues(values as Record<string, unknown>));
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
