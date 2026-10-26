import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { DISCOVERED_ASSET_LIFECYCLE_STAGE_VALUES } from './types';

export const discoveredAssetBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  jobId: z.string({ required_error: 'Job Id is required' }).uuid('Job Id must be a UUID'),
  assetType: z.string({ required_error: 'Asset Type is required' }).trim().min(1, 'Asset Type is required').max(255, 'Asset Type must be at most 255 characters'),
  fingerprint: z.string({ required_error: 'Fingerprint is required' }).trim().min(1, 'Fingerprint is required').max(255, 'Fingerprint must be at most 255 characters'),
  rawPayload: z.string().trim().max(4000, 'Raw Payload must be at most 4000 characters').optional(),
  matchedAssetId: z.string({ required_error: 'Matched Asset Id is required' }).uuid('Matched Asset Id must be a UUID').optional(),
  effectiveFrom: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective From must be YYYY-MM-DD').optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(DISCOVERED_ASSET_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
});

export const discoveredAssetSchema = discoveredAssetBaseSchema
  .refine((v) => !v.effectiveFrom || !v.effectiveTo || v.effectiveTo >= v.effectiveFrom, {
    path: ['effectiveTo'],
    message: 'effectiveTo must not be earlier than effectiveFrom',
  });

export type DiscoveredAssetValid = z.infer<typeof discoveredAssetSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanDiscoveredAssetValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateDiscoveredAsset(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = discoveredAssetSchema.safeParse(cleanDiscoveredAssetValues(values as Record<string, unknown>));
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
