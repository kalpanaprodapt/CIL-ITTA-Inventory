import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { TOPOLOGY_NODE_CRITICALITY_VALUES } from './types';

export const topologyNodeBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  viewId: z.string({ required_error: 'View Id is required' }).uuid('View Id must be a UUID'),
  entityRef: z.string({ required_error: 'Entity Ref is required' }).trim().min(1, 'Entity Ref is required').max(255, 'Entity Ref must be at most 255 characters'),
  nodeType: z.string().trim().max(255, 'Node Type must be at most 255 characters').optional(),
  xPos: z.number({ required_error: 'X Pos is required', invalid_type_error: 'X Pos must be a number' }).optional(),
  yPos: z.number({ required_error: 'Y Pos is required', invalid_type_error: 'Y Pos must be a number' }).optional(),
  layer: z.number({ required_error: 'Layer is required', invalid_type_error: 'Layer must be a number' }).int('Layer must be a whole number').min(0, 'Layer must not be negative').optional(),
  criticality: z.enum(TOPOLOGY_NODE_CRITICALITY_VALUES).optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
  notes: z.string().trim().max(4000, 'Notes must be at most 4000 characters').optional(),
  effectiveFrom: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective From must be YYYY-MM-DD').optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
});

export const topologyNodeSchema = topologyNodeBaseSchema
  .refine((v) => !v.effectiveFrom || !v.effectiveTo || v.effectiveTo >= v.effectiveFrom, {
    path: ['effectiveTo'],
    message: 'effectiveTo must not be earlier than effectiveFrom',
  });

export type TopologyNodeValid = z.infer<typeof topologyNodeSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanTopologyNodeValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateTopologyNode(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = topologyNodeSchema.safeParse(cleanTopologyNodeValues(values as Record<string, unknown>));
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
