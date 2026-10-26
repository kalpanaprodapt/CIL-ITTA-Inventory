import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { IMPORT_BATCH_BATCH_STATE_VALUES, IMPORT_BATCH_LIFECYCLE_STAGE_VALUES } from './types';

export const importBatchBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  fileName: z.string({ required_error: 'File Name is required' }).trim().min(1, 'File Name is required').max(255, 'File Name must be at most 255 characters'),
  entityType: z.string({ required_error: 'Entity Type is required' }).trim().min(1, 'Entity Type is required').max(255, 'Entity Type must be at most 255 characters'),
  totalRows: z.number({ required_error: 'Total Rows is required', invalid_type_error: 'Total Rows must be a number' }).int('Total Rows must be a whole number').min(0, 'Total Rows must not be negative').optional(),
  processedRows: z.number({ required_error: 'Processed Rows is required', invalid_type_error: 'Processed Rows must be a number' }).int('Processed Rows must be a whole number').min(0, 'Processed Rows must not be negative').optional(),
  failedRows: z.number({ required_error: 'Failed Rows is required', invalid_type_error: 'Failed Rows must be a number' }).int('Failed Rows must be a whole number').min(0, 'Failed Rows must not be negative').optional(),
  batchState: z.enum(IMPORT_BATCH_BATCH_STATE_VALUES).optional(),
  effectiveFrom: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective From must be YYYY-MM-DD').optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(IMPORT_BATCH_LIFECYCLE_STAGE_VALUES).optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
});

export const importBatchSchema = importBatchBaseSchema
  .refine((v) => !v.effectiveFrom || !v.effectiveTo || v.effectiveTo >= v.effectiveFrom, {
    path: ['effectiveTo'],
    message: 'effectiveTo must not be earlier than effectiveFrom',
  });

export type ImportBatchValid = z.infer<typeof importBatchSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanImportBatchValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateImportBatch(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = importBatchSchema.safeParse(cleanImportBatchValues(values as Record<string, unknown>));
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
