import { describe, expect, it } from 'vitest';
import { cleanCapacityPlanValues, validateCapacityPlan } from '../schema';
import { capacityPlanInput } from './fixtures';

describe('capacityPlan schema', () => {
  it('accepts the fixture', () => {
    expect(validateCapacityPlan(capacityPlanInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanCapacityPlanValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateCapacityPlan({ ...capacityPlanInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...capacityPlanInput };
    delete values.code;
    const result = validateCapacityPlan(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateCapacityPlan({ ...capacityPlanInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...capacityPlanInput };
    delete values.name;
    const result = validateCapacityPlan(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateCapacityPlan({ ...capacityPlanInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...capacityPlanInput };
    delete values.status;
    const result = validateCapacityPlan(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateCapacityPlan({ ...capacityPlanInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateCapacityPlan({ ...capacityPlanInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects planName longer than 255', () => {
    const result = validateCapacityPlan({ ...capacityPlanInput, planName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.planName).toContain('Plan Name');
  });

  it('requires planName', () => {
    const values: Record<string, unknown> = { ...capacityPlanInput };
    delete values.planName;
    const result = validateCapacityPlan(values);
    expect(result.ok).toBe(false);
    expect(result.errors.planName).toBeDefined();
  });

  it('requires horizonMonths', () => {
    const values: Record<string, unknown> = { ...capacityPlanInput };
    delete values.horizonMonths;
    const result = validateCapacityPlan(values);
    expect(result.ok).toBe(false);
    expect(result.errors.horizonMonths).toBeDefined();
  });

  it('rejects negative horizonMonths', () => {
    const result = validateCapacityPlan({ ...capacityPlanInput, horizonMonths: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.horizonMonths).toContain('negative');
  });

  it('rejects negative targetUtilizationPct', () => {
    const result = validateCapacityPlan({ ...capacityPlanInput, targetUtilizationPct: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.targetUtilizationPct).toContain('negative');
  });

  it('rejects owner longer than 255', () => {
    const result = validateCapacityPlan({ ...capacityPlanInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects malformed date in approvedOn', () => {
    const result = validateCapacityPlan({ ...capacityPlanInput, approvedOn: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateCapacityPlan({ ...capacityPlanInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateCapacityPlan({ ...capacityPlanInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateCapacityPlan({ ...capacityPlanInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateCapacityPlan({ ...capacityPlanInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateCapacityPlan({ ...capacityPlanInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });
});
