import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { OPTICAL_CHANNEL_GRID_TYPE_VALUES, OPTICAL_CHANNEL_CRITICALITY_VALUES } from './types';

export const opticalChannelBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  wavelengthNm: z.number({ required_error: 'Wavelength Nm is required', invalid_type_error: 'Wavelength Nm must be a number' }).min(0, 'Wavelength Nm must not be negative'),
  channelNumber: z.number({ required_error: 'Channel Number is required', invalid_type_error: 'Channel Number must be a number' }).int('Channel Number must be a whole number').min(0, 'Channel Number must not be negative'),
  gridType: z.enum(OPTICAL_CHANNEL_GRID_TYPE_VALUES).optional(),
  bitrateGbps: z.number({ required_error: 'Bitrate Gbps is required', invalid_type_error: 'Bitrate Gbps must be a number' }).min(0, 'Bitrate Gbps must not be negative').optional(),
  systemId: z.string({ required_error: 'System Id is required' }).uuid('System Id must be a UUID').optional(),
  criticality: z.enum(OPTICAL_CHANNEL_CRITICALITY_VALUES).optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
  notes: z.string().trim().max(4000, 'Notes must be at most 4000 characters').optional(),
  effectiveFrom: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective From must be YYYY-MM-DD').optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
});

export const opticalChannelSchema = opticalChannelBaseSchema
  .refine((v) => !v.effectiveFrom || !v.effectiveTo || v.effectiveTo >= v.effectiveFrom, {
    path: ['effectiveTo'],
    message: 'effectiveTo must not be earlier than effectiveFrom',
  });

export type OpticalChannelValid = z.infer<typeof opticalChannelSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanOpticalChannelValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateOpticalChannel(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = opticalChannelSchema.safeParse(cleanOpticalChannelValues(values as Record<string, unknown>));
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
