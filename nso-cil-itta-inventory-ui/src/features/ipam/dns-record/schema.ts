import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { DNS_RECORD_RECORD_TYPE_VALUES, DNS_RECORD_CRITICALITY_VALUES } from './types';

export const dnsRecordBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  zoneName: z.string({ required_error: 'Zone Name is required' }).trim().min(1, 'Zone Name is required').max(255, 'Zone Name must be at most 255 characters'),
  recordType: z.enum(DNS_RECORD_RECORD_TYPE_VALUES).optional(),
  recordValue: z.string({ required_error: 'Record Value is required' }).trim().min(1, 'Record Value is required').max(255, 'Record Value must be at most 255 characters'),
  ttl: z.number({ required_error: 'Ttl is required', invalid_type_error: 'Ttl must be a number' }).int('Ttl must be a whole number').min(0, 'Ttl must not be negative').optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(DNS_RECORD_CRITICALITY_VALUES).optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
});

export const dnsRecordSchema = dnsRecordBaseSchema
;

export type DnsRecordValid = z.infer<typeof dnsRecordSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanDnsRecordValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateDnsRecord(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = dnsRecordSchema.safeParse(cleanDnsRecordValues(values as Record<string, unknown>));
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
