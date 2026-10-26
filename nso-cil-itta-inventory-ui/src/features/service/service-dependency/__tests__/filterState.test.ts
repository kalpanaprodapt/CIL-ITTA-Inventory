import { describe, expect, it } from 'vitest';
import { countServiceDependencyActiveFilters, parseServiceDependencyFilter, serializeServiceDependencyFilter, toServiceDependencyQuery } from '../filterState';

describe('serviceDependency filter state', () => {
  it('is empty by default', () => {
    expect(parseServiceDependencyFilter(new URLSearchParams())).toEqual({});
    expect(countServiceDependencyActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseServiceDependencyFilter(serializeServiceDependencyFilter(filter))).toEqual(filter);
    expect(toServiceDependencyQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseServiceDependencyFilter(serializeServiceDependencyFilter(filter))).toEqual(filter);
    expect(toServiceDependencyQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseServiceDependencyFilter(serializeServiceDependencyFilter(filter))).toEqual(filter);
    expect(toServiceDependencyQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseServiceDependencyFilter(serializeServiceDependencyFilter(filter))).toEqual(filter);
    expect(toServiceDependencyQuery(filter).externalRef).toBe('abc');
  });

  it('round trips serviceId', () => {
    const filter = { serviceId: '00000000-0000-4000-8000-000000000011' };
    expect(parseServiceDependencyFilter(serializeServiceDependencyFilter(filter))).toEqual(filter);
  });

  it('round trips dependsOnId', () => {
    const filter = { dependsOnId: '00000000-0000-4000-8000-000000000011' };
    expect(parseServiceDependencyFilter(serializeServiceDependencyFilter(filter))).toEqual(filter);
  });

  it('round trips dependencyType selection', () => {
    const filter = { dependencyType: ['HARD'] as never };
    expect(parseServiceDependencyFilter(serializeServiceDependencyFilter(filter))).toEqual(filter);
    expect(toServiceDependencyQuery(filter).dependencyType).toBe('HARD');
  });

  it('round trips impactScore range', () => {
    const filter = { impactScoreMin: 1, impactScoreMax: 9 };
    expect(parseServiceDependencyFilter(serializeServiceDependencyFilter(filter))).toEqual(filter);
    expect(toServiceDependencyQuery(filter).impactScoreMax).toBe(9);
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseServiceDependencyFilter(serializeServiceDependencyFilter(filter))).toEqual(filter);
    expect(toServiceDependencyQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseServiceDependencyFilter(serializeServiceDependencyFilter(filter))).toEqual(filter);
    expect(toServiceDependencyQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseServiceDependencyFilter(serializeServiceDependencyFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseServiceDependencyFilter(serializeServiceDependencyFilter(filter))).toEqual(filter);
  });
});
