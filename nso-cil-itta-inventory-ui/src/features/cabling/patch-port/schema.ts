import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { PATCH_PORT_LIFECYCLE_STAGE_VALUES } from './types';

export const patchPortBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  panelId: z.string({ required_error: 'Panel Id is required' }).uuid('Panel Id must be a UUID'),
  portNumber: z.number({ required_error: 'Port Number is required', invalid_type_error: 'Port Number must be a number' }).int('Port Number must be a whole number').min(0, 'Port Number must not be negative'),
  connectedCableId: z.string({ required_error: 'Connected Cable Id is required' }).uuid('Connected Cable Id must be a UUID').optional(),
  polarity: z.string().trim().max(255, 'Polarity must be at most 255 characters').optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(PATCH_PORT_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
});

export const patchPortSchema = patchPortBaseSchema
;

export type PatchPortValid = z.infer<typeof patchPortSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanPatchPortValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validatePatchPort(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = patchPortSchema.safeParse(cleanPatchPortValues(values as Record<string, unknown>));
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
