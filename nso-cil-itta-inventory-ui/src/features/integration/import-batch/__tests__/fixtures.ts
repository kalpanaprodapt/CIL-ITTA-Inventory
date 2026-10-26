import type { ImportBatch, ImportBatchInput } from '../types';

export const importBatchInput: ImportBatchInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  fileName: 'fileName-1',
  entityType: 'entityType-1',
  totalRows: 11,
  processedRows: 11,
  failedRows: 11,
  batchState: 'UPLOADED',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  code: 'IMPORT-0001',
  name: 'Import Batch one',
  status: 'ACTIVE',
};

export const importBatchFixture: ImportBatch = {
  ...importBatchInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const importBatchPage = {
  content: [importBatchFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
