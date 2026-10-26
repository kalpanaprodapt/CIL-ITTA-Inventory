import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { CONTRACT_CRITICALITY_VALUES } from './types';

export const contractBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  customerId: z.string({ required_error: 'Customer Id is required' }).uuid('Customer Id must be a UUID'),
  contractNumber: z.string({ required_error: 'Contract Number is required' }).trim().min(1, 'Contract Number is required').max(255, 'Contract Number must be at most 255 characters'),
  startDate: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Start Date must be YYYY-MM-DD'),
  endDate: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'End Date must be YYYY-MM-DD').optional(),
  termMonths: z.number({ required_error: 'Term Months is required', invalid_type_error: 'Term Months must be a number' }).int('Term Months must be a whole number').min(0, 'Term Months must not be negative').optional(),
  autoRenew: z.boolean().optional(),
  sourceSystem: z.string().trim().max(255, 'Source System must be at most 255 characters').optional(),
  region: z.string().trim().max(255, 'Region must be at most 255 characters').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(CONTRACT_CRITICALITY_VALUES).optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
});

export const contractSchema = contractBaseSchema
  .refine((v) => !v.startDate || !v.endDate || v.endDate >= v.startDate, {
    path: ['endDate'],
    message: 'endDate must not be earlier than startDate',
  });

export type ContractValid = z.infer<typeof contractSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanContractValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateContract(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = contractSchema.safeParse(cleanContractValues(values as Record<string, unknown>));
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
