import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { EVENT_SOURCE_PROTOCOL_VALUES, EVENT_SOURCE_LIFECYCLE_STAGE_VALUES } from './types';

export const eventSourceBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  sourceName: z.string({ required_error: 'Source Name is required' }).trim().min(1, 'Source Name is required').max(255, 'Source Name must be at most 255 characters'),
  protocol: z.enum(EVENT_SOURCE_PROTOCOL_VALUES).optional(),
  endpoint: z.string().trim().max(255, 'Endpoint must be at most 255 characters').optional(),
  heartbeatSeconds: z.number({ required_error: 'Heartbeat Seconds is required', invalid_type_error: 'Heartbeat Seconds must be a number' }).int('Heartbeat Seconds must be a whole number').min(0, 'Heartbeat Seconds must not be negative').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(EVENT_SOURCE_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
});

export const eventSourceSchema = eventSourceBaseSchema
;

export type EventSourceValid = z.infer<typeof eventSourceSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanEventSourceValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateEventSource(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = eventSourceSchema.safeParse(cleanEventSourceValues(values as Record<string, unknown>));
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
