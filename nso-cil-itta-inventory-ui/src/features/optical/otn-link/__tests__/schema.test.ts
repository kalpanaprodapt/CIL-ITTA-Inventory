import { describe, expect, it } from 'vitest';
import { cleanOtnLinkValues, validateOtnLink } from '../schema';
import { otnLinkInput } from './fixtures';

describe('otnLink schema', () => {
  it('accepts the fixture', () => {
    expect(validateOtnLink(otnLinkInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanOtnLinkValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateOtnLink({ ...otnLinkInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...otnLinkInput };
    delete values.code;
    const result = validateOtnLink(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateOtnLink({ ...otnLinkInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...otnLinkInput };
    delete values.name;
    const result = validateOtnLink(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateOtnLink({ ...otnLinkInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...otnLinkInput };
    delete values.status;
    const result = validateOtnLink(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateOtnLink({ ...otnLinkInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateOtnLink({ ...otnLinkInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires aNodeId', () => {
    const values: Record<string, unknown> = { ...otnLinkInput };
    delete values.aNodeId;
    const result = validateOtnLink(values);
    expect(result.ok).toBe(false);
    expect(result.errors.aNodeId).toBeDefined();
  });

  it('rejects malformed aNodeId', () => {
    const result = validateOtnLink({ ...otnLinkInput, aNodeId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.aNodeId).toContain('UUID');
  });

  it('requires zNodeId', () => {
    const values: Record<string, unknown> = { ...otnLinkInput };
    delete values.zNodeId;
    const result = validateOtnLink(values);
    expect(result.ok).toBe(false);
    expect(result.errors.zNodeId).toBeDefined();
  });

  it('rejects malformed zNodeId', () => {
    const result = validateOtnLink({ ...otnLinkInput, zNodeId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.zNodeId).toContain('UUID');
  });

  it('rejects unknown odu', () => {
    const result = validateOtnLink({ ...otnLinkInput, odu: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects negative latencyUs', () => {
    const result = validateOtnLink({ ...otnLinkInput, latencyUs: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.latencyUs).toContain('negative');
  });

  it('rejects protection longer than 255', () => {
    const result = validateOtnLink({ ...otnLinkInput, protection: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.protection).toContain('Protection');
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateOtnLink({ ...otnLinkInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateOtnLink({ ...otnLinkInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateOtnLink({ ...otnLinkInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateOtnLink({ ...otnLinkInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateOtnLink({ ...otnLinkInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });
});
