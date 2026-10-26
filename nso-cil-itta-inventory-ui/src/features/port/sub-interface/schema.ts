import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';


export const subInterfaceBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  interfaceId: z.string({ required_error: 'Interface Id is required' }).uuid('Interface Id must be a UUID'),
  vlanTag: z.number({ required_error: 'Vlan Tag is required', invalid_type_error: 'Vlan Tag must be a number' }).int('Vlan Tag must be a whole number').min(0, 'Vlan Tag must not be negative'),
  encapsulation: z.string().trim().max(255, 'Encapsulation must be at most 255 characters').optional(),
  ipv4Address: z.string().trim().max(255, 'Ipv4 Address must be at most 255 characters').optional(),
  ipv6Address: z.string().trim().max(255, 'Ipv6 Address must be at most 255 characters').optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
  notes: z.string().trim().max(4000, 'Notes must be at most 4000 characters').optional(),
  effectiveFrom: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective From must be YYYY-MM-DD').optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
});

export const subInterfaceSchema = subInterfaceBaseSchema
  .refine((v) => !v.effectiveFrom || !v.effectiveTo || v.effectiveTo >= v.effectiveFrom, {
    path: ['effectiveTo'],
    message: 'effectiveTo must not be earlier than effectiveFrom',
  });

export type SubInterfaceValid = z.infer<typeof subInterfaceSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanSubInterfaceValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateSubInterface(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = subInterfaceSchema.safeParse(cleanSubInterfaceValues(values as Record<string, unknown>));
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
