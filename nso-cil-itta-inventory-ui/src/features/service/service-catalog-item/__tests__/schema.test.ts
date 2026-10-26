import { describe, expect, it } from 'vitest';
import { cleanServiceCatalogItemValues, validateServiceCatalogItem } from '../schema';
import { serviceCatalogItemInput } from './fixtures';

describe('serviceCatalogItem schema', () => {
  it('accepts the fixture', () => {
    expect(validateServiceCatalogItem(serviceCatalogItemInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanServiceCatalogItemValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateServiceCatalogItem({ ...serviceCatalogItemInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...serviceCatalogItemInput };
    delete values.code;
    const result = validateServiceCatalogItem(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateServiceCatalogItem({ ...serviceCatalogItemInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...serviceCatalogItemInput };
    delete values.name;
    const result = validateServiceCatalogItem(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateServiceCatalogItem({ ...serviceCatalogItemInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...serviceCatalogItemInput };
    delete values.status;
    const result = validateServiceCatalogItem(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateServiceCatalogItem({ ...serviceCatalogItemInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateServiceCatalogItem({ ...serviceCatalogItemInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects itemName longer than 255', () => {
    const result = validateServiceCatalogItem({ ...serviceCatalogItemInput, itemName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.itemName).toContain('Item Name');
  });

  it('requires itemName', () => {
    const values: Record<string, unknown> = { ...serviceCatalogItemInput };
    delete values.itemName;
    const result = validateServiceCatalogItem(values);
    expect(result.ok).toBe(false);
    expect(result.errors.itemName).toBeDefined();
  });

  it('rejects unknown family', () => {
    const result = validateServiceCatalogItem({ ...serviceCatalogItemInput, family: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects negative basePrice', () => {
    const result = validateServiceCatalogItem({ ...serviceCatalogItemInput, basePrice: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.basePrice).toContain('negative');
  });

  it('rejects billingUnit longer than 255', () => {
    const result = validateServiceCatalogItem({ ...serviceCatalogItemInput, billingUnit: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.billingUnit).toContain('Billing Unit');
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateServiceCatalogItem({ ...serviceCatalogItemInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateServiceCatalogItem({ ...serviceCatalogItemInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateServiceCatalogItem({ ...serviceCatalogItemInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateServiceCatalogItem({ ...serviceCatalogItemInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateServiceCatalogItem({ ...serviceCatalogItemInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });
});
