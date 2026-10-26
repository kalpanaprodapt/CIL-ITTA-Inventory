import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { RADIO_UNIT_UNIT_TYPE_VALUES, RADIO_UNIT_LIFECYCLE_STAGE_VALUES } from './types';

export const radioUnitBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  siteId: z.string({ required_error: 'Site Id is required' }).uuid('Site Id must be a UUID'),
  unitType: z.enum(RADIO_UNIT_UNIT_TYPE_VALUES).optional(),
  bandMhz: z.number({ required_error: 'Band Mhz is required', invalid_type_error: 'Band Mhz must be a number' }).int('Band Mhz must be a whole number').min(0, 'Band Mhz must not be negative').optional(),
  txPowerDbm: z.number({ required_error: 'Tx Power Dbm is required', invalid_type_error: 'Tx Power Dbm must be a number' }).optional(),
  serialNumber: z.string().trim().max(255, 'Serial Number must be at most 255 characters').optional(),
  effectiveFrom: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective From must be YYYY-MM-DD').optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(RADIO_UNIT_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
});

export const radioUnitSchema = radioUnitBaseSchema
  .refine((v) => !v.effectiveFrom || !v.effectiveTo || v.effectiveTo >= v.effectiveFrom, {
    path: ['effectiveTo'],
    message: 'effectiveTo must not be earlier than effectiveFrom',
  });

export type RadioUnitValid = z.infer<typeof radioUnitSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanRadioUnitValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateRadioUnit(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = radioUnitSchema.safeParse(cleanRadioUnitValues(values as Record<string, unknown>));
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
