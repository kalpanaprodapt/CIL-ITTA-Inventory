import { describe, expect, it } from 'vitest';
import { cleanReconciliationRuleValues, validateReconciliationRule } from '../schema';
import { reconciliationRuleInput } from './fixtures';

describe('reconciliationRule schema', () => {
  it('accepts the fixture', () => {
    expect(validateReconciliationRule(reconciliationRuleInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanReconciliationRuleValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateReconciliationRule({ ...reconciliationRuleInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...reconciliationRuleInput };
    delete values.code;
    const result = validateReconciliationRule(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateReconciliationRule({ ...reconciliationRuleInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...reconciliationRuleInput };
    delete values.name;
    const result = validateReconciliationRule(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateReconciliationRule({ ...reconciliationRuleInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...reconciliationRuleInput };
    delete values.status;
    const result = validateReconciliationRule(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateReconciliationRule({ ...reconciliationRuleInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateReconciliationRule({ ...reconciliationRuleInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects ruleName longer than 255', () => {
    const result = validateReconciliationRule({ ...reconciliationRuleInput, ruleName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.ruleName).toContain('Rule Name');
  });

  it('requires ruleName', () => {
    const values: Record<string, unknown> = { ...reconciliationRuleInput };
    delete values.ruleName;
    const result = validateReconciliationRule(values);
    expect(result.ok).toBe(false);
    expect(result.errors.ruleName).toBeDefined();
  });

  it('rejects matchKey longer than 255', () => {
    const result = validateReconciliationRule({ ...reconciliationRuleInput, matchKey: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.matchKey).toContain('Match Key');
  });

  it('requires matchKey', () => {
    const values: Record<string, unknown> = { ...reconciliationRuleInput };
    delete values.matchKey;
    const result = validateReconciliationRule(values);
    expect(result.ok).toBe(false);
    expect(result.errors.matchKey).toBeDefined();
  });

  it('rejects unknown action', () => {
    const result = validateReconciliationRule({ ...reconciliationRuleInput, action: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('requires priority', () => {
    const values: Record<string, unknown> = { ...reconciliationRuleInput };
    delete values.priority;
    const result = validateReconciliationRule(values);
    expect(result.ok).toBe(false);
    expect(result.errors.priority).toBeDefined();
  });

  it('rejects negative priority', () => {
    const result = validateReconciliationRule({ ...reconciliationRuleInput, priority: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.priority).toContain('negative');
  });

  it('rejects expression longer than 4000', () => {
    const result = validateReconciliationRule({ ...reconciliationRuleInput, expression: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.expression).toContain('Expression');
  });

  it('rejects labels longer than 255', () => {
    const result = validateReconciliationRule({ ...reconciliationRuleInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateReconciliationRule({ ...reconciliationRuleInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateReconciliationRule({ ...reconciliationRuleInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateReconciliationRule({ ...reconciliationRuleInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateReconciliationRule({ ...reconciliationRuleInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });
});
