import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { LINE_CARD_CRITICALITY_VALUES } from './types';

export const lineCardBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  chassisId: z.string({ required_error: 'Chassis Id is required' }).uuid('Chassis Id must be a UUID'),
  slotNumber: z.number({ required_error: 'Slot Number is required', invalid_type_error: 'Slot Number must be a number' }).int('Slot Number must be a whole number').min(0, 'Slot Number must not be negative'),
  cardType: z.string().trim().max(255, 'Card Type must be at most 255 characters').optional(),
  portCount: z.number({ required_error: 'Port Count is required', invalid_type_error: 'Port Count must be a number' }).int('Port Count must be a whole number').min(0, 'Port Count must not be negative').optional(),
  serialNumber: z.string().trim().max(255, 'Serial Number must be at most 255 characters').optional(),
  firmware: z.string().trim().max(255, 'Firmware must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(LINE_CARD_CRITICALITY_VALUES).optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
  notes: z.string().trim().max(4000, 'Notes must be at most 4000 characters').optional(),
});

export const lineCardSchema = lineCardBaseSchema
;

export type LineCardValid = z.infer<typeof lineCardSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanLineCardValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateLineCard(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = lineCardSchema.safeParse(cleanLineCardValues(values as Record<string, unknown>));
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
