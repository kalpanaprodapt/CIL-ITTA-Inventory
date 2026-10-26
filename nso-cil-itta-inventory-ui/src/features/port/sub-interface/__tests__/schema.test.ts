import { describe, expect, it } from 'vitest';
import { cleanSubInterfaceValues, validateSubInterface } from '../schema';
import { subInterfaceInput } from './fixtures';

describe('subInterface schema', () => {
  it('accepts the fixture', () => {
    expect(validateSubInterface(subInterfaceInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanSubInterfaceValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateSubInterface({ ...subInterfaceInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...subInterfaceInput };
    delete values.code;
    const result = validateSubInterface(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateSubInterface({ ...subInterfaceInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...subInterfaceInput };
    delete values.name;
    const result = validateSubInterface(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateSubInterface({ ...subInterfaceInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...subInterfaceInput };
    delete values.status;
    const result = validateSubInterface(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateSubInterface({ ...subInterfaceInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateSubInterface({ ...subInterfaceInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires interfaceId', () => {
    const values: Record<string, unknown> = { ...subInterfaceInput };
    delete values.interfaceId;
    const result = validateSubInterface(values);
    expect(result.ok).toBe(false);
    expect(result.errors.interfaceId).toBeDefined();
  });

  it('rejects malformed interfaceId', () => {
    const result = validateSubInterface({ ...subInterfaceInput, interfaceId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.interfaceId).toContain('UUID');
  });

  it('requires vlanTag', () => {
    const values: Record<string, unknown> = { ...subInterfaceInput };
    delete values.vlanTag;
    const result = validateSubInterface(values);
    expect(result.ok).toBe(false);
    expect(result.errors.vlanTag).toBeDefined();
  });

  it('rejects negative vlanTag', () => {
    const result = validateSubInterface({ ...subInterfaceInput, vlanTag: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.vlanTag).toContain('negative');
  });

  it('rejects encapsulation longer than 255', () => {
    const result = validateSubInterface({ ...subInterfaceInput, encapsulation: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.encapsulation).toContain('Encapsulation');
  });

  it('rejects ipv4Address longer than 255', () => {
    const result = validateSubInterface({ ...subInterfaceInput, ipv4Address: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.ipv4Address).toContain('Ipv4 Address');
  });

  it('rejects ipv6Address longer than 255', () => {
    const result = validateSubInterface({ ...subInterfaceInput, ipv6Address: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.ipv6Address).toContain('Ipv6 Address');
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateSubInterface({ ...subInterfaceInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateSubInterface({ ...subInterfaceInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateSubInterface({ ...subInterfaceInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateSubInterface({ ...subInterfaceInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateSubInterface({ ...subInterfaceInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });
});
