import { describe, expect, it } from 'vitest';
import { cleanAuditRecordValues, validateAuditRecord } from '../schema';
import { auditRecordInput } from './fixtures';

describe('auditRecord schema', () => {
  it('accepts the fixture', () => {
    expect(validateAuditRecord(auditRecordInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanAuditRecordValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateAuditRecord({ ...auditRecordInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...auditRecordInput };
    delete values.code;
    const result = validateAuditRecord(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateAuditRecord({ ...auditRecordInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...auditRecordInput };
    delete values.name;
    const result = validateAuditRecord(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateAuditRecord({ ...auditRecordInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...auditRecordInput };
    delete values.status;
    const result = validateAuditRecord(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateAuditRecord({ ...auditRecordInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateAuditRecord({ ...auditRecordInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects actor longer than 255', () => {
    const result = validateAuditRecord({ ...auditRecordInput, actor: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.actor).toContain('Actor');
  });

  it('requires actor', () => {
    const values: Record<string, unknown> = { ...auditRecordInput };
    delete values.actor;
    const result = validateAuditRecord(values);
    expect(result.ok).toBe(false);
    expect(result.errors.actor).toBeDefined();
  });

  it('rejects action longer than 255', () => {
    const result = validateAuditRecord({ ...auditRecordInput, action: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.action).toContain('Action');
  });

  it('requires action', () => {
    const values: Record<string, unknown> = { ...auditRecordInput };
    delete values.action;
    const result = validateAuditRecord(values);
    expect(result.ok).toBe(false);
    expect(result.errors.action).toBeDefined();
  });

  it('rejects entityRef longer than 255', () => {
    const result = validateAuditRecord({ ...auditRecordInput, entityRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.entityRef).toContain('Entity Ref');
  });

  it('requires entityRef', () => {
    const values: Record<string, unknown> = { ...auditRecordInput };
    delete values.entityRef;
    const result = validateAuditRecord(values);
    expect(result.ok).toBe(false);
    expect(result.errors.entityRef).toBeDefined();
  });

  it('requires occurredAt', () => {
    const values: Record<string, unknown> = { ...auditRecordInput };
    delete values.occurredAt;
    const result = validateAuditRecord(values);
    expect(result.ok).toBe(false);
    expect(result.errors.occurredAt).toBeDefined();
  });

  it('rejects beforeState longer than 4000', () => {
    const result = validateAuditRecord({ ...auditRecordInput, beforeState: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.beforeState).toContain('Before State');
  });

  it('rejects afterState longer than 4000', () => {
    const result = validateAuditRecord({ ...auditRecordInput, afterState: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.afterState).toContain('After State');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateAuditRecord({ ...auditRecordInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateAuditRecord({ ...auditRecordInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateAuditRecord({ ...auditRecordInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateAuditRecord({ ...auditRecordInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateAuditRecord({ ...auditRecordInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });
});
