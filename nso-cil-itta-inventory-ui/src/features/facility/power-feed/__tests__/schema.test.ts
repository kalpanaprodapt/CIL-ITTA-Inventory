import { describe, expect, it } from 'vitest';
import { cleanPowerFeedValues, validatePowerFeed } from '../schema';
import { powerFeedInput } from './fixtures';

describe('powerFeed schema', () => {
  it('accepts the fixture', () => {
    expect(validatePowerFeed(powerFeedInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanPowerFeedValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validatePowerFeed({ ...powerFeedInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...powerFeedInput };
    delete values.code;
    const result = validatePowerFeed(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validatePowerFeed({ ...powerFeedInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...powerFeedInput };
    delete values.name;
    const result = validatePowerFeed(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validatePowerFeed({ ...powerFeedInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...powerFeedInput };
    delete values.status;
    const result = validatePowerFeed(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validatePowerFeed({ ...powerFeedInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validatePowerFeed({ ...powerFeedInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires roomId', () => {
    const values: Record<string, unknown> = { ...powerFeedInput };
    delete values.roomId;
    const result = validatePowerFeed(values);
    expect(result.ok).toBe(false);
    expect(result.errors.roomId).toBeDefined();
  });

  it('rejects malformed roomId', () => {
    const result = validatePowerFeed({ ...powerFeedInput, roomId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.roomId).toContain('UUID');
  });

  it('rejects unknown feedType', () => {
    const result = validatePowerFeed({ ...powerFeedInput, feedType: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects negative voltage', () => {
    const result = validatePowerFeed({ ...powerFeedInput, voltage: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.voltage).toContain('negative');
  });

  it('rejects negative amperage', () => {
    const result = validatePowerFeed({ ...powerFeedInput, amperage: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.amperage).toContain('negative');
  });

  it('rejects phase longer than 255', () => {
    const result = validatePowerFeed({ ...powerFeedInput, phase: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.phase).toContain('Phase');
  });

  it('rejects breakerLabel longer than 255', () => {
    const result = validatePowerFeed({ ...powerFeedInput, breakerLabel: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.breakerLabel).toContain('Breaker Label');
  });

  it('rejects redundancyGroup longer than 255', () => {
    const result = validatePowerFeed({ ...powerFeedInput, redundancyGroup: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.redundancyGroup).toContain('Redundancy Group');
  });

  it('rejects owner longer than 255', () => {
    const result = validatePowerFeed({ ...powerFeedInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validatePowerFeed({ ...powerFeedInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validatePowerFeed({ ...powerFeedInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validatePowerFeed({ ...powerFeedInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validatePowerFeed({ ...powerFeedInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });
});
