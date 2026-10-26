import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { CLOUD_ACCOUNT_PROVIDER_VALUES, CLOUD_ACCOUNT_LIFECYCLE_STAGE_VALUES } from './types';

export const cloudAccountBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  provider: z.enum(CLOUD_ACCOUNT_PROVIDER_VALUES).optional(),
  accountRef: z.string({ required_error: 'Account Ref is required' }).trim().min(1, 'Account Ref is required').max(255, 'Account Ref must be at most 255 characters'),
  subscriptionName: z.string().trim().max(255, 'Subscription Name must be at most 255 characters').optional(),
  monthlyBudget: z.number({ required_error: 'Monthly Budget is required', invalid_type_error: 'Monthly Budget must be a number' }).min(0, 'Monthly Budget must not be negative').optional(),
  environment: z.string().trim().max(255, 'Environment must be at most 255 characters').optional(),
  notes: z.string().trim().max(4000, 'Notes must be at most 4000 characters').optional(),
  effectiveFrom: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective From must be YYYY-MM-DD').optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(CLOUD_ACCOUNT_LIFECYCLE_STAGE_VALUES).optional(),
});

export const cloudAccountSchema = cloudAccountBaseSchema
  .refine((v) => !v.effectiveFrom || !v.effectiveTo || v.effectiveTo >= v.effectiveFrom, {
    path: ['effectiveTo'],
    message: 'effectiveTo must not be earlier than effectiveFrom',
  });

export type CloudAccountValid = z.infer<typeof cloudAccountSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanCloudAccountValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateCloudAccount(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = cloudAccountSchema.safeParse(cleanCloudAccountValues(values as Record<string, unknown>));
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
