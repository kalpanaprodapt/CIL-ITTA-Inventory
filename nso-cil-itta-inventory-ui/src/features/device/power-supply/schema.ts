import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { POWER_SUPPLY_CRITICALITY_VALUES } from './types';

export const powerSupplyBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  chassisId: z.string({ required_error: 'Chassis Id is required' }).uuid('Chassis Id must be a UUID'),
  slotNumber: z.number({ required_error: 'Slot Number is required', invalid_type_error: 'Slot Number must be a number' }).int('Slot Number must be a whole number').min(0, 'Slot Number must not be negative').optional(),
  wattage: z.number({ required_error: 'Wattage is required', invalid_type_error: 'Wattage must be a number' }).int('Wattage must be a whole number').min(0, 'Wattage must not be negative').optional(),
  feedId: z.string({ required_error: 'Feed Id is required' }).uuid('Feed Id must be a UUID').optional(),
  serialNumber: z.string().trim().max(255, 'Serial Number must be at most 255 characters').optional(),
  efficiency: z.number({ required_error: 'Efficiency is required', invalid_type_error: 'Efficiency must be a number' }).min(0, 'Efficiency must not be negative').optional(),
  criticality: z.enum(POWER_SUPPLY_CRITICALITY_VALUES).optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
  notes: z.string().trim().max(4000, 'Notes must be at most 4000 characters').optional(),
  effectiveFrom: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective From must be YYYY-MM-DD').optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
});

export const powerSupplySchema = powerSupplyBaseSchema
  .refine((v) => !v.effectiveFrom || !v.effectiveTo || v.effectiveTo >= v.effectiveFrom, {
    path: ['effectiveTo'],
    message: 'effectiveTo must not be earlier than effectiveFrom',
  });

export type PowerSupplyValid = z.infer<typeof powerSupplySchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanPowerSupplyValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validatePowerSupply(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = powerSupplySchema.safeParse(cleanPowerSupplyValues(values as Record<string, unknown>));
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
