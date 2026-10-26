import { describe, expect, it } from 'vitest';
import { countCloudAccountActiveFilters, parseCloudAccountFilter, serializeCloudAccountFilter, toCloudAccountQuery } from '../filterState';

describe('cloudAccount filter state', () => {
  it('is empty by default', () => {
    expect(parseCloudAccountFilter(new URLSearchParams())).toEqual({});
    expect(countCloudAccountActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseCloudAccountFilter(serializeCloudAccountFilter(filter))).toEqual(filter);
    expect(toCloudAccountQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseCloudAccountFilter(serializeCloudAccountFilter(filter))).toEqual(filter);
    expect(toCloudAccountQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseCloudAccountFilter(serializeCloudAccountFilter(filter))).toEqual(filter);
    expect(toCloudAccountQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseCloudAccountFilter(serializeCloudAccountFilter(filter))).toEqual(filter);
    expect(toCloudAccountQuery(filter).externalRef).toBe('abc');
  });

  it('round trips provider selection', () => {
    const filter = { provider: ['AZURE'] as never };
    expect(parseCloudAccountFilter(serializeCloudAccountFilter(filter))).toEqual(filter);
    expect(toCloudAccountQuery(filter).provider).toBe('AZURE');
  });

  it('round trips accountRef', () => {
    const filter = { accountRef: 'abc' };
    expect(parseCloudAccountFilter(serializeCloudAccountFilter(filter))).toEqual(filter);
    expect(toCloudAccountQuery(filter).accountRef).toBe('abc');
  });

  it('round trips subscriptionName', () => {
    const filter = { subscriptionName: 'abc' };
    expect(parseCloudAccountFilter(serializeCloudAccountFilter(filter))).toEqual(filter);
    expect(toCloudAccountQuery(filter).subscriptionName).toBe('abc');
  });

  it('round trips monthlyBudget range', () => {
    const filter = { monthlyBudgetMin: 1, monthlyBudgetMax: 9 };
    expect(parseCloudAccountFilter(serializeCloudAccountFilter(filter))).toEqual(filter);
    expect(toCloudAccountQuery(filter).monthlyBudgetMax).toBe(9);
  });

  it('round trips environment', () => {
    const filter = { environment: 'abc' };
    expect(parseCloudAccountFilter(serializeCloudAccountFilter(filter))).toEqual(filter);
    expect(toCloudAccountQuery(filter).environment).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseCloudAccountFilter(serializeCloudAccountFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseCloudAccountFilter(serializeCloudAccountFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseCloudAccountFilter(serializeCloudAccountFilter(filter))).toEqual(filter);
    expect(toCloudAccountQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseCloudAccountFilter(serializeCloudAccountFilter(filter))).toEqual(filter);
    expect(toCloudAccountQuery(filter).lifecycleStage).toBe('PLAN');
  });
});
