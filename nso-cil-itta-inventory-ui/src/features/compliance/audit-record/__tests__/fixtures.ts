import type { AuditRecord, AuditRecordInput } from '../types';

export const auditRecordInput: AuditRecordInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  actor: 'actor-1',
  action: 'action-1',
  entityRef: 'entityRef-1',
  occurredAt: '2026-02-11T10:15:30Z',
  beforeState: 'beforeState-1',
  afterState: 'afterState-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  code: 'AUDITR-0001',
  name: 'Audit Record one',
  status: 'ACTIVE',
};

export const auditRecordFixture: AuditRecord = {
  ...auditRecordInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const auditRecordPage = {
  content: [auditRecordFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
