import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { INTEGRATION_ENDPOINT_AUTH_TYPE_VALUES, INTEGRATION_ENDPOINT_CRITICALITY_VALUES } from './types';

export const integrationEndpointBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  endpointName: z.string({ required_error: 'Endpoint Name is required' }).trim().min(1, 'Endpoint Name is required').max(255, 'Endpoint Name must be at most 255 characters'),
  targetSystem: z.string({ required_error: 'Target System is required' }).trim().min(1, 'Target System is required').max(255, 'Target System must be at most 255 characters'),
  baseUrl: z.string({ required_error: 'Base Url is required' }).trim().min(1, 'Base Url is required').max(255, 'Base Url must be at most 255 characters'),
  authType: z.enum(INTEGRATION_ENDPOINT_AUTH_TYPE_VALUES).optional(),
  timeoutMs: z.number({ required_error: 'Timeout Ms is required', invalid_type_error: 'Timeout Ms must be a number' }).int('Timeout Ms must be a whole number').min(0, 'Timeout Ms must not be negative').optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(INTEGRATION_ENDPOINT_CRITICALITY_VALUES).optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
});

export const integrationEndpointSchema = integrationEndpointBaseSchema
;

export type IntegrationEndpointValid = z.infer<typeof integrationEndpointSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanIntegrationEndpointValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateIntegrationEndpoint(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = integrationEndpointSchema.safeParse(cleanIntegrationEndpointValues(values as Record<string, unknown>));
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
