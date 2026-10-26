import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { WEBHOOK_SUBSCRIPTION_LIFECYCLE_STAGE_VALUES } from './types';

export const webhookSubscriptionBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  callbackUrl: z.string({ required_error: 'Callback Url is required' }).trim().min(1, 'Callback Url is required').max(255, 'Callback Url must be at most 255 characters'),
  eventTypes: z.string({ required_error: 'Event Types is required' }).trim().min(1, 'Event Types is required').max(255, 'Event Types must be at most 255 characters'),
  secretRef: z.string().trim().max(255, 'Secret Ref must be at most 255 characters').optional(),
  active: z.boolean().optional(),
  retryLimit: z.number({ required_error: 'Retry Limit is required', invalid_type_error: 'Retry Limit must be a number' }).int('Retry Limit must be a whole number').min(0, 'Retry Limit must not be negative').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(WEBHOOK_SUBSCRIPTION_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
});

export const webhookSubscriptionSchema = webhookSubscriptionBaseSchema
;

export type WebhookSubscriptionValid = z.infer<typeof webhookSubscriptionSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanWebhookSubscriptionValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateWebhookSubscription(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = webhookSubscriptionSchema.safeParse(cleanWebhookSubscriptionValues(values as Record<string, unknown>));
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
