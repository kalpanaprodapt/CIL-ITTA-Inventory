import type { PurchaseOrder, PurchaseOrderInput } from '../types';

export const purchaseOrderInput: PurchaseOrderInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  poNumber: 'poNumber-1',
  vendorId: '00000000-0000-4000-8000-000000000011',
  orderedOn: '2026-02-11',
  totalAmount: 13.5,
  currency: 'currency-1',
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  code: 'PURCHA-0001',
  name: 'Purchase Order one',
  status: 'ACTIVE',
};

export const purchaseOrderFixture: PurchaseOrder = {
  ...purchaseOrderInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const purchaseOrderPage = {
  content: [purchaseOrderFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
