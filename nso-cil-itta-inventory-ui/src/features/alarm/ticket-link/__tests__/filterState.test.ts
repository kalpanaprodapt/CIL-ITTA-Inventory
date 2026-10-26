import { describe, expect, it } from 'vitest';
import { countTicketLinkActiveFilters, parseTicketLinkFilter, serializeTicketLinkFilter, toTicketLinkQuery } from '../filterState';

describe('ticketLink filter state', () => {
  it('is empty by default', () => {
    expect(parseTicketLinkFilter(new URLSearchParams())).toEqual({});
    expect(countTicketLinkActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseTicketLinkFilter(serializeTicketLinkFilter(filter))).toEqual(filter);
    expect(toTicketLinkQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseTicketLinkFilter(serializeTicketLinkFilter(filter))).toEqual(filter);
    expect(toTicketLinkQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseTicketLinkFilter(serializeTicketLinkFilter(filter))).toEqual(filter);
    expect(toTicketLinkQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseTicketLinkFilter(serializeTicketLinkFilter(filter))).toEqual(filter);
    expect(toTicketLinkQuery(filter).externalRef).toBe('abc');
  });

  it('round trips alarmId', () => {
    const filter = { alarmId: '00000000-0000-4000-8000-000000000011' };
    expect(parseTicketLinkFilter(serializeTicketLinkFilter(filter))).toEqual(filter);
  });

  it('round trips ticketSystem selection', () => {
    const filter = { ticketSystem: ['SERVICENOW'] as never };
    expect(parseTicketLinkFilter(serializeTicketLinkFilter(filter))).toEqual(filter);
    expect(toTicketLinkQuery(filter).ticketSystem).toBe('SERVICENOW');
  });

  it('round trips ticketNumber', () => {
    const filter = { ticketNumber: 'abc' };
    expect(parseTicketLinkFilter(serializeTicketLinkFilter(filter))).toEqual(filter);
    expect(toTicketLinkQuery(filter).ticketNumber).toBe('abc');
  });

  it('round trips linkedAt window', () => {
    const filter = { linkedAtFrom: '2026-01-01', linkedAtTo: '2026-02-01' };
    expect(parseTicketLinkFilter(serializeTicketLinkFilter(filter))).toEqual(filter);
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseTicketLinkFilter(serializeTicketLinkFilter(filter))).toEqual(filter);
    expect(toTicketLinkQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseTicketLinkFilter(serializeTicketLinkFilter(filter))).toEqual(filter);
    expect(toTicketLinkQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseTicketLinkFilter(serializeTicketLinkFilter(filter))).toEqual(filter);
    expect(toTicketLinkQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseTicketLinkFilter(serializeTicketLinkFilter(filter))).toEqual(filter);
  });
});
