import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { SPLICE_LIFECYCLE_STAGE_VALUES, SPLICE_CRITICALITY_VALUES } from './types';

export const spliceBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  enclosureId: z.string({ required_error: 'Enclosure Id is required' }).trim().min(1, 'Enclosure Id is required').max(255, 'Enclosure Id must be at most 255 characters'),
  aStrandId: z.string({ required_error: 'A Strand Id is required' }).uuid('A Strand Id must be a UUID').optional(),
  zStrandId: z.string({ required_error: 'Z Strand Id is required' }).uuid('Z Strand Id must be a UUID').optional(),
  lossDb: z.number({ required_error: 'Loss Db is required', invalid_type_error: 'Loss Db must be a number' }).optional(),
  splicedOn: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Spliced On must be YYYY-MM-DD').optional(),
  lifecycleStage: z.enum(SPLICE_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(SPLICE_CRITICALITY_VALUES).optional(),
});

export const spliceSchema = spliceBaseSchema
;

export type SpliceValid = z.infer<typeof spliceSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanSpliceValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateSplice(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = spliceSchema.safeParse(cleanSpliceValues(values as Record<string, unknown>));
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
