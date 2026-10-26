import { describe, expect, it } from 'vitest';
import { countWebhookSubscriptionActiveFilters, parseWebhookSubscriptionFilter, serializeWebhookSubscriptionFilter, toWebhookSubscriptionQuery } from '../filterState';

describe('webhookSubscription filter state', () => {
  it('is empty by default', () => {
    expect(parseWebhookSubscriptionFilter(new URLSearchParams())).toEqual({});
    expect(countWebhookSubscriptionActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseWebhookSubscriptionFilter(serializeWebhookSubscriptionFilter(filter))).toEqual(filter);
    expect(toWebhookSubscriptionQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseWebhookSubscriptionFilter(serializeWebhookSubscriptionFilter(filter))).toEqual(filter);
    expect(toWebhookSubscriptionQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseWebhookSubscriptionFilter(serializeWebhookSubscriptionFilter(filter))).toEqual(filter);
    expect(toWebhookSubscriptionQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseWebhookSubscriptionFilter(serializeWebhookSubscriptionFilter(filter))).toEqual(filter);
    expect(toWebhookSubscriptionQuery(filter).externalRef).toBe('abc');
  });

  it('round trips callbackUrl', () => {
    const filter = { callbackUrl: 'abc' };
    expect(parseWebhookSubscriptionFilter(serializeWebhookSubscriptionFilter(filter))).toEqual(filter);
    expect(toWebhookSubscriptionQuery(filter).callbackUrl).toBe('abc');
  });

  it('round trips eventTypes', () => {
    const filter = { eventTypes: 'abc' };
    expect(parseWebhookSubscriptionFilter(serializeWebhookSubscriptionFilter(filter))).toEqual(filter);
    expect(toWebhookSubscriptionQuery(filter).eventTypes).toBe('abc');
  });

  it('round trips secretRef', () => {
    const filter = { secretRef: 'abc' };
    expect(parseWebhookSubscriptionFilter(serializeWebhookSubscriptionFilter(filter))).toEqual(filter);
    expect(toWebhookSubscriptionQuery(filter).secretRef).toBe('abc');
  });

  it('round trips active flag', () => {
    const filter = { active: false };
    expect(parseWebhookSubscriptionFilter(serializeWebhookSubscriptionFilter(filter))).toEqual(filter);
  });

  it('round trips retryLimit range', () => {
    const filter = { retryLimitMin: 1, retryLimitMax: 9 };
    expect(parseWebhookSubscriptionFilter(serializeWebhookSubscriptionFilter(filter))).toEqual(filter);
    expect(toWebhookSubscriptionQuery(filter).retryLimitMax).toBe(9);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseWebhookSubscriptionFilter(serializeWebhookSubscriptionFilter(filter))).toEqual(filter);
    expect(toWebhookSubscriptionQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseWebhookSubscriptionFilter(serializeWebhookSubscriptionFilter(filter))).toEqual(filter);
    expect(toWebhookSubscriptionQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseWebhookSubscriptionFilter(serializeWebhookSubscriptionFilter(filter))).toEqual(filter);
    expect(toWebhookSubscriptionQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseWebhookSubscriptionFilter(serializeWebhookSubscriptionFilter(filter))).toEqual(filter);
    expect(toWebhookSubscriptionQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseWebhookSubscriptionFilter(serializeWebhookSubscriptionFilter(filter))).toEqual(filter);
    expect(toWebhookSubscriptionQuery(filter).owner).toBe('abc');
  });
});
