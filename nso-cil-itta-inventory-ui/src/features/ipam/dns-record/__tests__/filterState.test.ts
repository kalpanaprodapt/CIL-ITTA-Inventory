import { describe, expect, it } from 'vitest';
import { countDnsRecordActiveFilters, parseDnsRecordFilter, serializeDnsRecordFilter, toDnsRecordQuery } from '../filterState';

describe('dnsRecord filter state', () => {
  it('is empty by default', () => {
    expect(parseDnsRecordFilter(new URLSearchParams())).toEqual({});
    expect(countDnsRecordActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseDnsRecordFilter(serializeDnsRecordFilter(filter))).toEqual(filter);
    expect(toDnsRecordQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseDnsRecordFilter(serializeDnsRecordFilter(filter))).toEqual(filter);
    expect(toDnsRecordQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseDnsRecordFilter(serializeDnsRecordFilter(filter))).toEqual(filter);
    expect(toDnsRecordQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseDnsRecordFilter(serializeDnsRecordFilter(filter))).toEqual(filter);
    expect(toDnsRecordQuery(filter).externalRef).toBe('abc');
  });

  it('round trips zoneName', () => {
    const filter = { zoneName: 'abc' };
    expect(parseDnsRecordFilter(serializeDnsRecordFilter(filter))).toEqual(filter);
    expect(toDnsRecordQuery(filter).zoneName).toBe('abc');
  });

  it('round trips recordType selection', () => {
    const filter = { recordType: ['A'] as never };
    expect(parseDnsRecordFilter(serializeDnsRecordFilter(filter))).toEqual(filter);
    expect(toDnsRecordQuery(filter).recordType).toBe('A');
  });

  it('round trips recordValue', () => {
    const filter = { recordValue: 'abc' };
    expect(parseDnsRecordFilter(serializeDnsRecordFilter(filter))).toEqual(filter);
    expect(toDnsRecordQuery(filter).recordValue).toBe('abc');
  });

  it('round trips ttl range', () => {
    const filter = { ttlMin: 1, ttlMax: 9 };
    expect(parseDnsRecordFilter(serializeDnsRecordFilter(filter))).toEqual(filter);
    expect(toDnsRecordQuery(filter).ttlMax).toBe(9);
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseDnsRecordFilter(serializeDnsRecordFilter(filter))).toEqual(filter);
    expect(toDnsRecordQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseDnsRecordFilter(serializeDnsRecordFilter(filter))).toEqual(filter);
    expect(toDnsRecordQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseDnsRecordFilter(serializeDnsRecordFilter(filter))).toEqual(filter);
    expect(toDnsRecordQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseDnsRecordFilter(serializeDnsRecordFilter(filter))).toEqual(filter);
    expect(toDnsRecordQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseDnsRecordFilter(serializeDnsRecordFilter(filter))).toEqual(filter);
    expect(toDnsRecordQuery(filter).costCenter).toBe('abc');
  });
});
