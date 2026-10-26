import { describe, expect, it } from 'vitest';
import { cleanComplianceRuleValues, validateComplianceRule } from '../schema';
import { complianceRuleInput } from './fixtures';

describe('complianceRule schema', () => {
  it('accepts the fixture', () => {
    expect(validateComplianceRule(complianceRuleInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanComplianceRuleValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateComplianceRule({ ...complianceRuleInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...complianceRuleInput };
    delete values.code;
    const result = validateComplianceRule(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateComplianceRule({ ...complianceRuleInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...complianceRuleInput };
    delete values.name;
    const result = validateComplianceRule(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateComplianceRule({ ...complianceRuleInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...complianceRuleInput };
    delete values.status;
    const result = validateComplianceRule(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateComplianceRule({ ...complianceRuleInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateComplianceRule({ ...complianceRuleInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects ruleCode longer than 255', () => {
    const result = validateComplianceRule({ ...complianceRuleInput, ruleCode: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.ruleCode).toContain('Rule Code');
  });

  it('requires ruleCode', () => {
    const values: Record<string, unknown> = { ...complianceRuleInput };
    delete values.ruleCode;
    const result = validateComplianceRule(values);
    expect(result.ok).toBe(false);
    expect(result.errors.ruleCode).toBeDefined();
  });

  it('rejects unknown framework', () => {
    const result = validateComplianceRule({ ...complianceRuleInput, framework: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects unknown severity', () => {
    const result = validateComplianceRule({ ...complianceRuleInput, severity: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects expression longer than 4000', () => {
    const result = validateComplianceRule({ ...complianceRuleInput, expression: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.expression).toContain('Expression');
  });

  it('rejects region longer than 255', () => {
    const result = validateComplianceRule({ ...complianceRuleInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateComplianceRule({ ...complianceRuleInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateComplianceRule({ ...complianceRuleInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateComplianceRule({ ...complianceRuleInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateComplianceRule({ ...complianceRuleInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });
});
