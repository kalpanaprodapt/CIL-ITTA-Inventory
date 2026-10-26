import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { IP_SUBNET_IP_VERSION_VALUES, IP_SUBNET_CRITICALITY_VALUES } from './types';

export const ipSubnetBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  cidr: z.string({ required_error: 'Cidr is required' }).trim().min(1, 'Cidr is required').max(255, 'Cidr must be at most 255 characters'),
  ipVersion: z.enum(IP_SUBNET_IP_VERSION_VALUES).optional(),
  vrfId: z.string({ required_error: 'Vrf Id is required' }).uuid('Vrf Id must be a UUID').optional(),
  gateway: z.string().trim().max(255, 'Gateway must be at most 255 characters').optional(),
  vlanId: z.string({ required_error: 'Vlan Id is required' }).uuid('Vlan Id must be a UUID').optional(),
  utilizationPct: z.number({ required_error: 'Utilization Pct is required', invalid_type_error: 'Utilization Pct must be a number' }).min(0, 'Utilization Pct must not be negative').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(IP_SUBNET_CRITICALITY_VALUES).optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
  notes: z.string().trim().max(4000, 'Notes must be at most 4000 characters').optional(),
  effectiveFrom: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective From must be YYYY-MM-DD').optional(),
});

export const ipSubnetSchema = ipSubnetBaseSchema
;

export type IpSubnetValid = z.infer<typeof ipSubnetSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanIpSubnetValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateIpSubnet(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = ipSubnetSchema.safeParse(cleanIpSubnetValues(values as Record<string, unknown>));
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
