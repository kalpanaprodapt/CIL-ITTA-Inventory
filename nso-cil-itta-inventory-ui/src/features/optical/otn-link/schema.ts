import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { OTN_LINK_ODU_VALUES, OTN_LINK_LIFECYCLE_STAGE_VALUES } from './types';

export const otnLinkBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  aNodeId: z.string({ required_error: 'A Node Id is required' }).uuid('A Node Id must be a UUID'),
  zNodeId: z.string({ required_error: 'Z Node Id is required' }).uuid('Z Node Id must be a UUID'),
  odu: z.enum(OTN_LINK_ODU_VALUES).optional(),
  latencyUs: z.number({ required_error: 'Latency Us is required', invalid_type_error: 'Latency Us must be a number' }).min(0, 'Latency Us must not be negative').optional(),
  protection: z.string().trim().max(255, 'Protection must be at most 255 characters').optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(OTN_LINK_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
});

export const otnLinkSchema = otnLinkBaseSchema
;

export type OtnLinkValid = z.infer<typeof otnLinkSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanOtnLinkValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateOtnLink(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = otnLinkSchema.safeParse(cleanOtnLinkValues(values as Record<string, unknown>));
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
