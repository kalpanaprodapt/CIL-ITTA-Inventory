import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { SKU_CRITICALITY_VALUES } from './types';

export const skuBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  modelId: z.string({ required_error: 'Model Id is required' }).uuid('Model Id must be a UUID'),
  skuCode: z.string({ required_error: 'Sku Code is required' }).trim().min(1, 'Sku Code is required').max(255, 'Sku Code must be at most 255 characters'),
  listPrice: z.number({ required_error: 'List Price is required', invalid_type_error: 'List Price must be a number' }).min(0, 'List Price must not be negative').optional(),
  currency: z.string().trim().max(255, 'Currency must be at most 255 characters').optional(),
  leadTimeDays: z.number({ required_error: 'Lead Time Days is required', invalid_type_error: 'Lead Time Days must be a number' }).int('Lead Time Days must be a whole number').min(0, 'Lead Time Days must not be negative').optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(SKU_CRITICALITY_VALUES).optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
});

export const skuSchema = skuBaseSchema
;

export type SkuValid = z.infer<typeof skuSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanSkuValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateSku(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = skuSchema.safeParse(cleanSkuValues(values as Record<string, unknown>));
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
