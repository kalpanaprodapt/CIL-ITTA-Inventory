import { describe, expect, it } from 'vitest';
import { countPowerSupplyActiveFilters, parsePowerSupplyFilter, serializePowerSupplyFilter, toPowerSupplyQuery } from '../filterState';

describe('powerSupply filter state', () => {
  it('is empty by default', () => {
    expect(parsePowerSupplyFilter(new URLSearchParams())).toEqual({});
    expect(countPowerSupplyActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parsePowerSupplyFilter(serializePowerSupplyFilter(filter))).toEqual(filter);
    expect(toPowerSupplyQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parsePowerSupplyFilter(serializePowerSupplyFilter(filter))).toEqual(filter);
    expect(toPowerSupplyQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parsePowerSupplyFilter(serializePowerSupplyFilter(filter))).toEqual(filter);
    expect(toPowerSupplyQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parsePowerSupplyFilter(serializePowerSupplyFilter(filter))).toEqual(filter);
    expect(toPowerSupplyQuery(filter).externalRef).toBe('abc');
  });

  it('round trips chassisId', () => {
    const filter = { chassisId: '00000000-0000-4000-8000-000000000011' };
    expect(parsePowerSupplyFilter(serializePowerSupplyFilter(filter))).toEqual(filter);
  });

  it('round trips slotNumber range', () => {
    const filter = { slotNumberMin: 1, slotNumberMax: 9 };
    expect(parsePowerSupplyFilter(serializePowerSupplyFilter(filter))).toEqual(filter);
    expect(toPowerSupplyQuery(filter).slotNumberMax).toBe(9);
  });

  it('round trips wattage range', () => {
    const filter = { wattageMin: 1, wattageMax: 9 };
    expect(parsePowerSupplyFilter(serializePowerSupplyFilter(filter))).toEqual(filter);
    expect(toPowerSupplyQuery(filter).wattageMax).toBe(9);
  });

  it('round trips feedId', () => {
    const filter = { feedId: '00000000-0000-4000-8000-000000000011' };
    expect(parsePowerSupplyFilter(serializePowerSupplyFilter(filter))).toEqual(filter);
  });

  it('round trips serialNumber', () => {
    const filter = { serialNumber: 'abc' };
    expect(parsePowerSupplyFilter(serializePowerSupplyFilter(filter))).toEqual(filter);
    expect(toPowerSupplyQuery(filter).serialNumber).toBe('abc');
  });

  it('round trips efficiency range', () => {
    const filter = { efficiencyMin: 1, efficiencyMax: 9 };
    expect(parsePowerSupplyFilter(serializePowerSupplyFilter(filter))).toEqual(filter);
    expect(toPowerSupplyQuery(filter).efficiencyMax).toBe(9);
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parsePowerSupplyFilter(serializePowerSupplyFilter(filter))).toEqual(filter);
    expect(toPowerSupplyQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parsePowerSupplyFilter(serializePowerSupplyFilter(filter))).toEqual(filter);
    expect(toPowerSupplyQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parsePowerSupplyFilter(serializePowerSupplyFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parsePowerSupplyFilter(serializePowerSupplyFilter(filter))).toEqual(filter);
  });
});
