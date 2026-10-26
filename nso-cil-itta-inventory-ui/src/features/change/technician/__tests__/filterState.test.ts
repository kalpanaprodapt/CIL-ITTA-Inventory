import { describe, expect, it } from 'vitest';
import { countTechnicianActiveFilters, parseTechnicianFilter, serializeTechnicianFilter, toTechnicianQuery } from '../filterState';

describe('technician filter state', () => {
  it('is empty by default', () => {
    expect(parseTechnicianFilter(new URLSearchParams())).toEqual({});
    expect(countTechnicianActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseTechnicianFilter(serializeTechnicianFilter(filter))).toEqual(filter);
    expect(toTechnicianQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseTechnicianFilter(serializeTechnicianFilter(filter))).toEqual(filter);
    expect(toTechnicianQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseTechnicianFilter(serializeTechnicianFilter(filter))).toEqual(filter);
    expect(toTechnicianQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseTechnicianFilter(serializeTechnicianFilter(filter))).toEqual(filter);
    expect(toTechnicianQuery(filter).externalRef).toBe('abc');
  });

  it('round trips employeeId', () => {
    const filter = { employeeId: 'abc' };
    expect(parseTechnicianFilter(serializeTechnicianFilter(filter))).toEqual(filter);
    expect(toTechnicianQuery(filter).employeeId).toBe('abc');
  });

  it('round trips fullName', () => {
    const filter = { fullName: 'abc' };
    expect(parseTechnicianFilter(serializeTechnicianFilter(filter))).toEqual(filter);
    expect(toTechnicianQuery(filter).fullName).toBe('abc');
  });

  it('round trips skillSet', () => {
    const filter = { skillSet: 'abc' };
    expect(parseTechnicianFilter(serializeTechnicianFilter(filter))).toEqual(filter);
    expect(toTechnicianQuery(filter).skillSet).toBe('abc');
  });

  it('round trips homeSiteId', () => {
    const filter = { homeSiteId: '00000000-0000-4000-8000-000000000011' };
    expect(parseTechnicianFilter(serializeTechnicianFilter(filter))).toEqual(filter);
  });

  it('round trips certificationLevel range', () => {
    const filter = { certificationLevelMin: 1, certificationLevelMax: 9 };
    expect(parseTechnicianFilter(serializeTechnicianFilter(filter))).toEqual(filter);
    expect(toTechnicianQuery(filter).certificationLevelMax).toBe(9);
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseTechnicianFilter(serializeTechnicianFilter(filter))).toEqual(filter);
    expect(toTechnicianQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseTechnicianFilter(serializeTechnicianFilter(filter))).toEqual(filter);
    expect(toTechnicianQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseTechnicianFilter(serializeTechnicianFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseTechnicianFilter(serializeTechnicianFilter(filter))).toEqual(filter);
  });
});
