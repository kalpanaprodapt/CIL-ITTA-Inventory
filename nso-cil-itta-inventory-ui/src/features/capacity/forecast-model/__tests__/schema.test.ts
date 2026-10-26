import { describe, expect, it } from 'vitest';
import { cleanForecastModelValues, validateForecastModel } from '../schema';
import { forecastModelInput } from './fixtures';

describe('forecastModel schema', () => {
  it('accepts the fixture', () => {
    expect(validateForecastModel(forecastModelInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanForecastModelValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateForecastModel({ ...forecastModelInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...forecastModelInput };
    delete values.code;
    const result = validateForecastModel(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateForecastModel({ ...forecastModelInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...forecastModelInput };
    delete values.name;
    const result = validateForecastModel(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateForecastModel({ ...forecastModelInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...forecastModelInput };
    delete values.status;
    const result = validateForecastModel(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateForecastModel({ ...forecastModelInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateForecastModel({ ...forecastModelInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects modelName longer than 255', () => {
    const result = validateForecastModel({ ...forecastModelInput, modelName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.modelName).toContain('Model Name');
  });

  it('requires modelName', () => {
    const values: Record<string, unknown> = { ...forecastModelInput };
    delete values.modelName;
    const result = validateForecastModel(values);
    expect(result.ok).toBe(false);
    expect(result.errors.modelName).toBeDefined();
  });

  it('rejects unknown algorithm', () => {
    const result = validateForecastModel({ ...forecastModelInput, algorithm: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects negative mape', () => {
    const result = validateForecastModel({ ...forecastModelInput, mape: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.mape).toContain('negative');
  });

  it('rejects unknown criticality', () => {
    const result = validateForecastModel({ ...forecastModelInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateForecastModel({ ...forecastModelInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateForecastModel({ ...forecastModelInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateForecastModel({ ...forecastModelInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateForecastModel({ ...forecastModelInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });
});
