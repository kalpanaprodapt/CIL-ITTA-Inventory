import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { WDM_SYSTEM_BAND_VALUES, WDM_SYSTEM_LIFECYCLE_STAGE_VALUES } from './types';

export const wdmSystemBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  systemName: z.string({ required_error: 'System Name is required' }).trim().min(1, 'System Name is required').max(255, 'System Name must be at most 255 characters'),
  channelCapacity: z.number({ required_error: 'Channel Capacity is required', invalid_type_error: 'Channel Capacity must be a number' }).int('Channel Capacity must be a whole number').min(0, 'Channel Capacity must not be negative'),
  band: z.enum(WDM_SYSTEM_BAND_VALUES).optional(),
  spanCount: z.number({ required_error: 'Span Count is required', invalid_type_error: 'Span Count must be a number' }).int('Span Count must be a whole number').min(0, 'Span Count must not be negative').optional(),
  vendorId: z.string({ required_error: 'Vendor Id is required' }).uuid('Vendor Id must be a UUID').optional(),
  notes: z.string().trim().max(4000, 'Notes must be at most 4000 characters').optional(),
  effectiveFrom: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective From must be YYYY-MM-DD').optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(WDM_SYSTEM_LIFECYCLE_STAGE_VALUES).optional(),
});

export const wdmSystemSchema = wdmSystemBaseSchema
  .refine((v) => !v.effectiveFrom || !v.effectiveTo || v.effectiveTo >= v.effectiveFrom, {
    path: ['effectiveTo'],
    message: 'effectiveTo must not be earlier than effectiveFrom',
  });

export type WdmSystemValid = z.infer<typeof wdmSystemSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanWdmSystemValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateWdmSystem(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = wdmSystemSchema.safeParse(cleanWdmSystemValues(values as Record<string, unknown>));
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
