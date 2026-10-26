import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { INTERFACE_OPER_STATE_VALUES, INTERFACE_CRITICALITY_VALUES } from './types';

export const interfaceBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  portId: z.string({ required_error: 'Port Id is required' }).uuid('Port Id must be a UUID'),
  ifName: z.string({ required_error: 'If Name is required' }).trim().min(1, 'If Name is required').max(255, 'If Name must be at most 255 characters'),
  ifIndex: z.number({ required_error: 'If Index is required', invalid_type_error: 'If Index must be a number' }).int('If Index must be a whole number').min(0, 'If Index must not be negative').optional(),
  macAddress: z.string().trim().max(255, 'Mac Address must be at most 255 characters').optional(),
  mtu: z.number({ required_error: 'Mtu is required', invalid_type_error: 'Mtu must be a number' }).int('Mtu must be a whole number').min(0, 'Mtu must not be negative').optional(),
  operState: z.enum(INTERFACE_OPER_STATE_VALUES).optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(INTERFACE_CRITICALITY_VALUES).optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
  notes: z.string().trim().max(4000, 'Notes must be at most 4000 characters').optional(),
  effectiveFrom: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective From must be YYYY-MM-DD').optional(),
});

export const interfaceSchema = interfaceBaseSchema
;

export type InterfaceValid = z.infer<typeof interfaceSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanInterfaceValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateInterface(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = interfaceSchema.safeParse(cleanInterfaceValues(values as Record<string, unknown>));
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
