import { describe, expect, it } from 'vitest';
import { cleanEventSourceValues, validateEventSource } from '../schema';
import { eventSourceInput } from './fixtures';

describe('eventSource schema', () => {
  it('accepts the fixture', () => {
    expect(validateEventSource(eventSourceInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanEventSourceValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateEventSource({ ...eventSourceInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...eventSourceInput };
    delete values.code;
    const result = validateEventSource(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateEventSource({ ...eventSourceInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...eventSourceInput };
    delete values.name;
    const result = validateEventSource(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateEventSource({ ...eventSourceInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...eventSourceInput };
    delete values.status;
    const result = validateEventSource(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateEventSource({ ...eventSourceInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateEventSource({ ...eventSourceInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects sourceName longer than 255', () => {
    const result = validateEventSource({ ...eventSourceInput, sourceName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceName).toContain('Source Name');
  });

  it('requires sourceName', () => {
    const values: Record<string, unknown> = { ...eventSourceInput };
    delete values.sourceName;
    const result = validateEventSource(values);
    expect(result.ok).toBe(false);
    expect(result.errors.sourceName).toBeDefined();
  });

  it('rejects unknown protocol', () => {
    const result = validateEventSource({ ...eventSourceInput, protocol: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects endpoint longer than 255', () => {
    const result = validateEventSource({ ...eventSourceInput, endpoint: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.endpoint).toContain('Endpoint');
  });

  it('rejects negative heartbeatSeconds', () => {
    const result = validateEventSource({ ...eventSourceInput, heartbeatSeconds: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.heartbeatSeconds).toContain('negative');
  });

  it('rejects labels longer than 255', () => {
    const result = validateEventSource({ ...eventSourceInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateEventSource({ ...eventSourceInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateEventSource({ ...eventSourceInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateEventSource({ ...eventSourceInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateEventSource({ ...eventSourceInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });
});
