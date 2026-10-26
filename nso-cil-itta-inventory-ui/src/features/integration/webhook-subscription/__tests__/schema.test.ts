import { describe, expect, it } from 'vitest';
import { cleanWebhookSubscriptionValues, validateWebhookSubscription } from '../schema';
import { webhookSubscriptionInput } from './fixtures';

describe('webhookSubscription schema', () => {
  it('accepts the fixture', () => {
    expect(validateWebhookSubscription(webhookSubscriptionInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanWebhookSubscriptionValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateWebhookSubscription({ ...webhookSubscriptionInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...webhookSubscriptionInput };
    delete values.code;
    const result = validateWebhookSubscription(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateWebhookSubscription({ ...webhookSubscriptionInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...webhookSubscriptionInput };
    delete values.name;
    const result = validateWebhookSubscription(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateWebhookSubscription({ ...webhookSubscriptionInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...webhookSubscriptionInput };
    delete values.status;
    const result = validateWebhookSubscription(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateWebhookSubscription({ ...webhookSubscriptionInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateWebhookSubscription({ ...webhookSubscriptionInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects callbackUrl longer than 255', () => {
    const result = validateWebhookSubscription({ ...webhookSubscriptionInput, callbackUrl: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.callbackUrl).toContain('Callback Url');
  });

  it('requires callbackUrl', () => {
    const values: Record<string, unknown> = { ...webhookSubscriptionInput };
    delete values.callbackUrl;
    const result = validateWebhookSubscription(values);
    expect(result.ok).toBe(false);
    expect(result.errors.callbackUrl).toBeDefined();
  });

  it('rejects eventTypes longer than 255', () => {
    const result = validateWebhookSubscription({ ...webhookSubscriptionInput, eventTypes: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.eventTypes).toContain('Event Types');
  });

  it('requires eventTypes', () => {
    const values: Record<string, unknown> = { ...webhookSubscriptionInput };
    delete values.eventTypes;
    const result = validateWebhookSubscription(values);
    expect(result.ok).toBe(false);
    expect(result.errors.eventTypes).toBeDefined();
  });

  it('rejects secretRef longer than 255', () => {
    const result = validateWebhookSubscription({ ...webhookSubscriptionInput, secretRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.secretRef).toContain('Secret Ref');
  });

  it('rejects negative retryLimit', () => {
    const result = validateWebhookSubscription({ ...webhookSubscriptionInput, retryLimit: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.retryLimit).toContain('negative');
  });

  it('rejects labels longer than 255', () => {
    const result = validateWebhookSubscription({ ...webhookSubscriptionInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateWebhookSubscription({ ...webhookSubscriptionInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateWebhookSubscription({ ...webhookSubscriptionInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateWebhookSubscription({ ...webhookSubscriptionInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateWebhookSubscription({ ...webhookSubscriptionInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });
});
