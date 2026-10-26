import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { VIRTUAL_MACHINE_POWER_STATE_VALUES, VIRTUAL_MACHINE_CRITICALITY_VALUES } from './types';

export const virtualMachineBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  hypervisorId: z.string({ required_error: 'Hypervisor Id is required' }).uuid('Hypervisor Id must be a UUID'),
  vmName: z.string({ required_error: 'Vm Name is required' }).trim().min(1, 'Vm Name is required').max(255, 'Vm Name must be at most 255 characters'),
  vcpu: z.number({ required_error: 'Vcpu is required', invalid_type_error: 'Vcpu must be a number' }).int('Vcpu must be a whole number').min(0, 'Vcpu must not be negative'),
  memoryGb: z.number({ required_error: 'Memory Gb is required', invalid_type_error: 'Memory Gb must be a number' }).int('Memory Gb must be a whole number').min(0, 'Memory Gb must not be negative'),
  diskGb: z.number({ required_error: 'Disk Gb is required', invalid_type_error: 'Disk Gb must be a number' }).int('Disk Gb must be a whole number').min(0, 'Disk Gb must not be negative').optional(),
  powerState: z.enum(VIRTUAL_MACHINE_POWER_STATE_VALUES).optional(),
  criticality: z.enum(VIRTUAL_MACHINE_CRITICALITY_VALUES).optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
  notes: z.string().trim().max(4000, 'Notes must be at most 4000 characters').optional(),
  effectiveFrom: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective From must be YYYY-MM-DD').optional(),
  effectiveTo: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective To must be YYYY-MM-DD').optional(),
});

export const virtualMachineSchema = virtualMachineBaseSchema
  .refine((v) => !v.effectiveFrom || !v.effectiveTo || v.effectiveTo >= v.effectiveFrom, {
    path: ['effectiveTo'],
    message: 'effectiveTo must not be earlier than effectiveFrom',
  });

export type VirtualMachineValid = z.infer<typeof virtualMachineSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanVirtualMachineValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateVirtualMachine(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = virtualMachineSchema.safeParse(cleanVirtualMachineValues(values as Record<string, unknown>));
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
