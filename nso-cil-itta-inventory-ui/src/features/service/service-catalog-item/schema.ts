import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { SERVICE_CATALOG_ITEM_FAMILY_VALUES, SERVICE_CATALOG_ITEM_LIFECYCLE_STAGE_VALUES } from './types';

export const serviceCatalogItemBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  itemName: z.string({ required_error: 'Item Name is required' }).trim().min(1, 'Item Name is required').max(255, 'Item Name must be at most 255 characters'),
  family: z.enum(SERVICE_CATALOG_ITEM_FAMILY_VALUES).optional(),
  basePrice: z.number({ required_error: 'Base Price is required', invalid_type_error: 'Base Price must be a number' }).min(0, 'Base Price must not be negative').optional(),
  billingUnit: z.string().trim().max(255, 'Billing Unit must be at most 255 characters').optional(),
  orderable: z.boolean().optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(SERVICE_CATALOG_ITEM_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
});

export const serviceCatalogItemSchema = serviceCatalogItemBaseSchema
;

export type ServiceCatalogItemValid = z.infer<typeof serviceCatalogItemSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanServiceCatalogItemValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateServiceCatalogItem(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = serviceCatalogItemSchema.safeParse(cleanServiceCatalogItemValues(values as Record<string, unknown>));
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
