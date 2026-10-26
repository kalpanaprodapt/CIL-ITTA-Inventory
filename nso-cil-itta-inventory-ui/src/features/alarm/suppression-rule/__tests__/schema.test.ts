import { describe, expect, it } from 'vitest';
import { cleanSuppressionRuleValues, validateSuppressionRule } from '../schema';
import { suppressionRuleInput } from './fixtures';

describe('suppressionRule schema', () => {
  it('accepts the fixture', () => {
    expect(validateSuppressionRule(suppressionRuleInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanSuppressionRuleValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateSuppressionRule({ ...suppressionRuleInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...suppressionRuleInput };
    delete values.code;
    const result = validateSuppressionRule(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateSuppressionRule({ ...suppressionRuleInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...suppressionRuleInput };
    delete values.name;
    const result = validateSuppressionRule(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateSuppressionRule({ ...suppressionRuleInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...suppressionRuleInput };
    delete values.status;
    const result = validateSuppressionRule(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateSuppressionRule({ ...suppressionRuleInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateSuppressionRule({ ...suppressionRuleInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects ruleName longer than 255', () => {
    const result = validateSuppressionRule({ ...suppressionRuleInput, ruleName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.ruleName).toContain('Rule Name');
  });

  it('requires ruleName', () => {
    const values: Record<string, unknown> = { ...suppressionRuleInput };
    delete values.ruleName;
    const result = validateSuppressionRule(values);
    expect(result.ok).toBe(false);
    expect(result.errors.ruleName).toBeDefined();
  });

  it('rejects matchExpression longer than 4000', () => {
    const result = validateSuppressionRule({ ...suppressionRuleInput, matchExpression: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.matchExpression).toContain('Match Expression');
  });

  it('requires matchExpression', () => {
    const values: Record<string, unknown> = { ...suppressionRuleInput };
    delete values.matchExpression;
    const result = validateSuppressionRule(values);
    expect(result.ok).toBe(false);
    expect(result.errors.matchExpression).toBeDefined();
  });

  it('rejects reason longer than 255', () => {
    const result = validateSuppressionRule({ ...suppressionRuleInput, reason: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.reason).toContain('Reason');
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateSuppressionRule({ ...suppressionRuleInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateSuppressionRule({ ...suppressionRuleInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateSuppressionRule({ ...suppressionRuleInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateSuppressionRule({ ...suppressionRuleInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateSuppressionRule({ ...suppressionRuleInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });
});
