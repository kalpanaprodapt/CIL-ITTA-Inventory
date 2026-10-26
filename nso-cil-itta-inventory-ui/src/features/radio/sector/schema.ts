import { z } from 'zod';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import { SECTOR_CRITICALITY_VALUES } from './types';

export const sectorBaseSchema = z.object({
  code: z.string({ required_error: 'Code is required' }).trim().min(1, 'Code is required').max(255, 'Code must be at most 255 characters'),
  name: z.string({ required_error: 'Name is required' }).trim().min(1, 'Name is required').max(255, 'Name must be at most 255 characters'),
  description: z.string().trim().max(4000, 'Description must be at most 4000 characters').optional(),
  status: z.enum(LIFECYCLE_STATUSES, { required_error: 'Status is required' }),
  externalRef: z.string().trim().max(255, 'External Ref must be at most 255 characters').optional(),
  baseStationId: z.string({ required_error: 'Base Station Id is required' }).uuid('Base Station Id must be a UUID'),
  sectorNumber: z.number({ required_error: 'Sector Number is required', invalid_type_error: 'Sector Number must be a number' }).int('Sector Number must be a whole number').min(0, 'Sector Number must not be negative'),
  pci: z.number({ required_error: 'Pci is required', invalid_type_error: 'Pci must be a number' }).int('Pci must be a whole number').min(0, 'Pci must not be negative').optional(),
  frequencyMhz: z.number({ required_error: 'Frequency Mhz is required', invalid_type_error: 'Frequency Mhz must be a number' }).int('Frequency Mhz must be a whole number').min(0, 'Frequency Mhz must not be negative').optional(),
  bandwidthMhz: z.number({ required_error: 'Bandwidth Mhz is required', invalid_type_error: 'Bandwidth Mhz must be a number' }).int('Bandwidth Mhz must be a whole number').min(0, 'Bandwidth Mhz must not be negative').optional(),
  owner: z.string().trim().max(255, 'Owner must be at most 255 characters').optional(),
  criticality: z.enum(SECTOR_CRITICALITY_VALUES).optional(),
  costCenter: z.string().trim().max(255, 'Cost Center must be at most 255 characters').optional(),
  notes: z.string().trim().max(4000, 'Notes must be at most 4000 characters').optional(),
  effectiveFrom: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Effective From must be YYYY-MM-DD').optional(),
});

export const sectorSchema = sectorBaseSchema
;

export type SectorValid = z.infer<typeof sectorSchema>;

/** Convert raw form state (empty strings, NaN) into values zod understands. */
export function cleanSectorValues(values: object): Record<string, unknown> {
  const out: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(values as Record<string, unknown>)) {
    if (value === '' || value === null || (typeof value === 'number' && Number.isNaN(value))) {
      continue;
    }
    out[key] = value;
  }
  return out;
}

export function validateSector(values: object): { ok: boolean; errors: Record<string, string> } {
  const result = sectorSchema.safeParse(cleanSectorValues(values as Record<string, unknown>));
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
