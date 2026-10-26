import { describe, expect, it } from 'vitest';
import { cleanChangeRequestValues, validateChangeRequest } from '../schema';
import { changeRequestInput } from './fixtures';

describe('changeRequest schema', () => {
  it('accepts the fixture', () => {
    expect(validateChangeRequest(changeRequestInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanChangeRequestValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateChangeRequest({ ...changeRequestInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...changeRequestInput };
    delete values.code;
    const result = validateChangeRequest(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateChangeRequest({ ...changeRequestInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...changeRequestInput };
    delete values.name;
    const result = validateChangeRequest(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateChangeRequest({ ...changeRequestInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...changeRequestInput };
    delete values.status;
    const result = validateChangeRequest(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateChangeRequest({ ...changeRequestInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateChangeRequest({ ...changeRequestInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects changeNumber longer than 255', () => {
    const result = validateChangeRequest({ ...changeRequestInput, changeNumber: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.changeNumber).toContain('Change Number');
  });

  it('requires changeNumber', () => {
    const values: Record<string, unknown> = { ...changeRequestInput };
    delete values.changeNumber;
    const result = validateChangeRequest(values);
    expect(result.ok).toBe(false);
    expect(result.errors.changeNumber).toBeDefined();
  });

  it('rejects unknown changeType', () => {
    const result = validateChangeRequest({ ...changeRequestInput, changeType: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects unknown riskLevel', () => {
    const result = validateChangeRequest({ ...changeRequestInput, riskLevel: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects notes longer than 4000', () => {
    const result = validateChangeRequest({ ...changeRequestInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateChangeRequest({ ...changeRequestInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateChangeRequest({ ...changeRequestInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateChangeRequest({ ...changeRequestInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateChangeRequest({ ...changeRequestInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });
});
