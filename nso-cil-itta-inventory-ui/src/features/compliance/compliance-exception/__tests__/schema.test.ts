import { describe, expect, it } from 'vitest';
import { cleanComplianceExceptionValues, validateComplianceException } from '../schema';
import { complianceExceptionInput } from './fixtures';

describe('complianceException schema', () => {
  it('accepts the fixture', () => {
    expect(validateComplianceException(complianceExceptionInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanComplianceExceptionValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateComplianceException({ ...complianceExceptionInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...complianceExceptionInput };
    delete values.code;
    const result = validateComplianceException(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateComplianceException({ ...complianceExceptionInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...complianceExceptionInput };
    delete values.name;
    const result = validateComplianceException(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateComplianceException({ ...complianceExceptionInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...complianceExceptionInput };
    delete values.status;
    const result = validateComplianceException(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateComplianceException({ ...complianceExceptionInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateComplianceException({ ...complianceExceptionInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires ruleId', () => {
    const values: Record<string, unknown> = { ...complianceExceptionInput };
    delete values.ruleId;
    const result = validateComplianceException(values);
    expect(result.ok).toBe(false);
    expect(result.errors.ruleId).toBeDefined();
  });

  it('rejects malformed ruleId', () => {
    const result = validateComplianceException({ ...complianceExceptionInput, ruleId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.ruleId).toContain('UUID');
  });

  it('rejects targetRef longer than 255', () => {
    const result = validateComplianceException({ ...complianceExceptionInput, targetRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.targetRef).toContain('Target Ref');
  });

  it('requires targetRef', () => {
    const values: Record<string, unknown> = { ...complianceExceptionInput };
    delete values.targetRef;
    const result = validateComplianceException(values);
    expect(result.ok).toBe(false);
    expect(result.errors.targetRef).toBeDefined();
  });

  it('rejects justification longer than 4000', () => {
    const result = validateComplianceException({ ...complianceExceptionInput, justification: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.justification).toContain('Justification');
  });

  it('requires justification', () => {
    const values: Record<string, unknown> = { ...complianceExceptionInput };
    delete values.justification;
    const result = validateComplianceException(values);
    expect(result.ok).toBe(false);
    expect(result.errors.justification).toBeDefined();
  });

  it('rejects approvedBy longer than 255', () => {
    const result = validateComplianceException({ ...complianceExceptionInput, approvedBy: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.approvedBy).toContain('Approved By');
  });

  it('rejects malformed date in expiresOn', () => {
    const result = validateComplianceException({ ...complianceExceptionInput, expiresOn: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateComplianceException({ ...complianceExceptionInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateComplianceException({ ...complianceExceptionInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateComplianceException({ ...complianceExceptionInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateComplianceException({ ...complianceExceptionInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateComplianceException({ ...complianceExceptionInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });
});
