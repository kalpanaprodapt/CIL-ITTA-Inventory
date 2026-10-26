import type { Invoice, InvoiceInput } from '../types';

export const invoiceInput: InvoiceInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  invoiceNumber: 'invoiceNumber-1',
  poId: '00000000-0000-4000-8000-000000000011',
  amount: 13.5,
  dueDate: '2026-02-11',
  paid: true,
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  code: 'INVOIC-0001',
  name: 'Invoice one',
  status: 'ACTIVE',
};

export const invoiceFixture: Invoice = {
  ...invoiceInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const invoicePage = {
  content: [invoiceFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
