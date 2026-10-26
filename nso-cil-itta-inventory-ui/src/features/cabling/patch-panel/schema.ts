import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { PATCH_PANEL_PANEL_TYPE_VALUES, PATCH_PANEL_LIFECYCLE_STAGE_VALUES } from './types';

export const patchPanelBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  roomId: z.string({ required_error: 'Room Id is required' }).uuid('Room Id must be a UUID'),
  rackId: z.string({ required_error: 'Rack Id is required' }).uuid('Rack Id must be a UUID').optional(),
  panelType: z.enum(PATCH_PANEL_PANEL_TYPE_VALUES).optional(),
  portCount: z.number({ required_error: 'Port Count is required', invalid_type_error: 'Port Count must be a number' }).int('Port Count must be a whole number').min(0, 'Port Count must not be negative'),
  label: z.string().trim().max(255, 'Label must be at most 255 characters').optional(),
  notes: z.string().trim().max(4000, 'Notes must be at most 4000 characters').optional(),
  effectiveFrom: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective From must be YYYY-MM-DD').optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
  labels: z.string().trim().max(255, 'Labels must be at most 255 characters').optional(),
  lifecycleStage: z.enum(PATCH_PANEL_LIFECYCLE_STAGE_VALUES).optional(),
});

export const patchPanelSchema = patchPanelBaseSchema
  .refine((v) => !v.effectiveFrom || !v.effectiveTo || v.effectiveTo >= v.effectiveFrom, {
    path: ['effectiveTo'],
    message: 'effectiveTo must not be earlier than effectiveFrom',
  });

export type PatchPanelValid = z.infer<typeof patchPanelSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanPatchPanelValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validatePatchPanel(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = patchPanelSchema.safeParse(cleanPatchPanelValues(values as Record<string, unknown>));
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
