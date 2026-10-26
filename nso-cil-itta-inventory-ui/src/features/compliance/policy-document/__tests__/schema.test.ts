import { describe, expect, it } from 'vitest';
import { cleanPolicyDocumentValues, validatePolicyDocument } from '../schema';
import { policyDocumentInput } from './fixtures';

describe('policyDocument schema', () => {
  it('accepts the fixture', () => {
    expect(validatePolicyDocument(policyDocumentInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanPolicyDocumentValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validatePolicyDocument({ ...policyDocumentInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...policyDocumentInput };
    delete values.code;
    const result = validatePolicyDocument(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validatePolicyDocument({ ...policyDocumentInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...policyDocumentInput };
    delete values.name;
    const result = validatePolicyDocument(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validatePolicyDocument({ ...policyDocumentInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...policyDocumentInput };
    delete values.status;
    const result = validatePolicyDocument(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validatePolicyDocument({ ...policyDocumentInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validatePolicyDocument({ ...policyDocumentInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects title longer than 255', () => {
    const result = validatePolicyDocument({ ...policyDocumentInput, title: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.title).toContain('Title');
  });

  it('requires title', () => {
    const values: Record<string, unknown> = { ...policyDocumentInput };
    delete values.title;
    const result = validatePolicyDocument(values);
    expect(result.ok).toBe(false);
    expect(result.errors.title).toBeDefined();
  });

  it('rejects docVersion longer than 255', () => {
    const result = validatePolicyDocument({ ...policyDocumentInput, docVersion: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.docVersion).toContain('Doc Version');
  });

  it('requires docVersion', () => {
    const values: Record<string, unknown> = { ...policyDocumentInput };
    delete values.docVersion;
    const result = validatePolicyDocument(values);
    expect(result.ok).toBe(false);
    expect(result.errors.docVersion).toBeDefined();
  });

  it('requires effectiveDate', () => {
    const values: Record<string, unknown> = { ...policyDocumentInput };
    delete values.effectiveDate;
    const result = validatePolicyDocument(values);
    expect(result.ok).toBe(false);
    expect(result.errors.effectiveDate).toBeDefined();
  });

  it('rejects malformed date in effectiveDate', () => {
    const result = validatePolicyDocument({ ...policyDocumentInput, effectiveDate: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects owner longer than 255', () => {
    const result = validatePolicyDocument({ ...policyDocumentInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects documentUrl longer than 255', () => {
    const result = validatePolicyDocument({ ...policyDocumentInput, documentUrl: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.documentUrl).toContain('Document Url');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validatePolicyDocument({ ...policyDocumentInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validatePolicyDocument({ ...policyDocumentInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validatePolicyDocument({ ...policyDocumentInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects unknown criticality', () => {
    const result = validatePolicyDocument({ ...policyDocumentInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validatePolicyDocument({ ...policyDocumentInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });
});
