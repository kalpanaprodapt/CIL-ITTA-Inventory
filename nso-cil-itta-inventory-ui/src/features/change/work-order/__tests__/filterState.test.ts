import { describe, expect, it } from 'vitest';
import { countWorkOrderActiveFilters, parseWorkOrderFilter, serializeWorkOrderFilter, toWorkOrderQuery } from '../filterState';

describe('workOrder filter state', () => {
  it('is empty by default', () => {
    expect(parseWorkOrderFilter(new URLSearchParams())).toEqual({});
    expect(countWorkOrderActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseWorkOrderFilter(serializeWorkOrderFilter(filter))).toEqual(filter);
    expect(toWorkOrderQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseWorkOrderFilter(serializeWorkOrderFilter(filter))).toEqual(filter);
    expect(toWorkOrderQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseWorkOrderFilter(serializeWorkOrderFilter(filter))).toEqual(filter);
    expect(toWorkOrderQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseWorkOrderFilter(serializeWorkOrderFilter(filter))).toEqual(filter);
    expect(toWorkOrderQuery(filter).externalRef).toBe('abc');
  });

  it('round trips workOrderNumber', () => {
    const filter = { workOrderNumber: 'abc' };
    expect(parseWorkOrderFilter(serializeWorkOrderFilter(filter))).toEqual(filter);
    expect(toWorkOrderQuery(filter).workOrderNumber).toBe('abc');
  });

  it('round trips workType', () => {
    const filter = { workType: 'abc' };
    expect(parseWorkOrderFilter(serializeWorkOrderFilter(filter))).toEqual(filter);
    expect(toWorkOrderQuery(filter).workType).toBe('abc');
  });

  it('round trips assignedTechnicianId', () => {
    const filter = { assignedTechnicianId: '00000000-0000-4000-8000-000000000011' };
    expect(parseWorkOrderFilter(serializeWorkOrderFilter(filter))).toEqual(filter);
  });

  it('round trips dueDate window', () => {
    const filter = { dueDateFrom: '2026-01-01', dueDateTo: '2026-02-01' };
    expect(parseWorkOrderFilter(serializeWorkOrderFilter(filter))).toEqual(filter);
  });

  it('round trips workState selection', () => {
    const filter = { workState: ['OPEN'] as never };
    expect(parseWorkOrderFilter(serializeWorkOrderFilter(filter))).toEqual(filter);
    expect(toWorkOrderQuery(filter).workState).toBe('OPEN');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseWorkOrderFilter(serializeWorkOrderFilter(filter))).toEqual(filter);
    expect(toWorkOrderQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseWorkOrderFilter(serializeWorkOrderFilter(filter))).toEqual(filter);
    expect(toWorkOrderQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseWorkOrderFilter(serializeWorkOrderFilter(filter))).toEqual(filter);
    expect(toWorkOrderQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseWorkOrderFilter(serializeWorkOrderFilter(filter))).toEqual(filter);
    expect(toWorkOrderQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseWorkOrderFilter(serializeWorkOrderFilter(filter))).toEqual(filter);
    expect(toWorkOrderQuery(filter).criticality).toBe('LOW');
  });
});
