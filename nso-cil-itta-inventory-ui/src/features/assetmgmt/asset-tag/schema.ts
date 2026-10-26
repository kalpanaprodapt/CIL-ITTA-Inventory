import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { ASSET_TAG_TAG_TYPE_VALUES, ASSET_TAG_CRITICALITY_VALUES } from './types';

export const assetTagBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  assetId: z.string({ required_error: 'Asset Id is required' }).uuid('Asset Id must be a UUID'),
  tagValue: z.string({ required_error: 'Tag Value is required' }).trim().min(1, 'Tag Value is required').max(255, 'Tag Value must be at most 255 characters'),
  tagType: z.enum(ASSET_TAG_TAG_TYPE_VALUES).optional(),
  printedOn: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Printed On must be YYYY-MM-DD').optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(ASSET_TAG_CRITICALITY_VALUES).optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
});

export const assetTagSchema = assetTagBaseSchema
;

export type AssetTagValid = z.infer<typeof assetTagSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanAssetTagValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateAssetTag(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = assetTagSchema.safeParse(cleanAssetTagValues(values as Record<string, unknown>));
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
