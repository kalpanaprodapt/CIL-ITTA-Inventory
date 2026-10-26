import { describe, expect, it } from 'vitest';
import { cleanWorkOrderValues, validateWorkOrder } from '../schema';
import { workOrderInput } from './fixtures';

describe('workOrder schema', () => {
  it('accepts the fixture', () => {
    expect(validateWorkOrder(workOrderInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanWorkOrderValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateWorkOrder({ ...workOrderInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...workOrderInput };
    delete values.code;
    const result = validateWorkOrder(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateWorkOrder({ ...workOrderInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...workOrderInput };
    delete values.name;
    const result = validateWorkOrder(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateWorkOrder({ ...workOrderInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...workOrderInput };
    delete values.status;
    const result = validateWorkOrder(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateWorkOrder({ ...workOrderInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateWorkOrder({ ...workOrderInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects workOrderNumber longer than 255', () => {
    const result = validateWorkOrder({ ...workOrderInput, workOrderNumber: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.workOrderNumber).toContain('Work Order Number');
  });

  it('requires workOrderNumber', () => {
    const values: Record<string, unknown> = { ...workOrderInput };
    delete values.workOrderNumber;
    const result = validateWorkOrder(values);
    expect(result.ok).toBe(false);
    expect(result.errors.workOrderNumber).toBeDefined();
  });

  it('rejects workType longer than 255', () => {
    const result = validateWorkOrder({ ...workOrderInput, workType: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.workType).toContain('Work Type');
  });

  it('requires workType', () => {
    const values: Record<string, unknown> = { ...workOrderInput };
    delete values.workType;
    const result = validateWorkOrder(values);
    expect(result.ok).toBe(false);
    expect(result.errors.workType).toBeDefined();
  });

  it('rejects malformed assignedTechnicianId', () => {
    const result = validateWorkOrder({ ...workOrderInput, assignedTechnicianId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.assignedTechnicianId).toContain('UUID');
  });

  it('rejects malformed date in dueDate', () => {
    const result = validateWorkOrder({ ...workOrderInput, dueDate: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects unknown workState', () => {
    const result = validateWorkOrder({ ...workOrderInput, workState: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateWorkOrder({ ...workOrderInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateWorkOrder({ ...workOrderInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateWorkOrder({ ...workOrderInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateWorkOrder({ ...workOrderInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateWorkOrder({ ...workOrderInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });
});
