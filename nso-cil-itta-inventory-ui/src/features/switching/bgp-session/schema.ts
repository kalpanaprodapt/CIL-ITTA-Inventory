import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { BGP_SESSION_SESSION_STATE_VALUES, BGP_SESSION_CRITICALITY_VALUES } from './types';

export const bgpSessionBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  localDeviceId: z.string({ required_error: 'Local Device Id is required' }).uuid('Local Device Id must be a UUID'),
  peerAddress: z.string({ required_error: 'Peer Address is required' }).trim().min(1, 'Peer Address is required').max(255, 'Peer Address must be at most 255 characters'),
  localAsn: z.number({ required_error: 'Local Asn is required', invalid_type_error: 'Local Asn must be a number' }).int('Local Asn must be a whole number').min(0, 'Local Asn must not be negative'),
  remoteAsn: z.number({ required_error: 'Remote Asn is required', invalid_type_error: 'Remote Asn must be a number' }).int('Remote Asn must be a whole number').min(0, 'Remote Asn must not be negative'),
  sessionState: z.enum(BGP_SESSION_SESSION_STATE_VALUES).optional(),
  prefixLimit: z.number({ required_error: 'Prefix Limit is required', invalid_type_error: 'Prefix Limit must be a number' }).int('Prefix Limit must be a whole number').min(0, 'Prefix Limit must not be negative').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(BGP_SESSION_CRITICALITY_VALUES).optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
  notes: z.string().trim().max(4000, 'Notes must be at most 4000 characters').optional(),
});

export const bgpSessionSchema = bgpSessionBaseSchema
;

export type BgpSessionValid = z.infer<typeof bgpSessionSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanBgpSessionValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateBgpSession(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = bgpSessionSchema.safeParse(cleanBgpSessionValues(values as Record<string, unknown>));
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
