import { describe, expect, it } from 'vitest';
import { cleanPatchPanelValues, validatePatchPanel } from '../schema';
import { patchPanelInput } from './fixtures';

describe('patchPanel schema', () => {
  it('accepts the fixture', () => {
    expect(validatePatchPanel(patchPanelInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanPatchPanelValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validatePatchPanel({ ...patchPanelInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...patchPanelInput };
    delete values.code;
    const result = validatePatchPanel(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validatePatchPanel({ ...patchPanelInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...patchPanelInput };
    delete values.name;
    const result = validatePatchPanel(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validatePatchPanel({ ...patchPanelInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...patchPanelInput };
    delete values.status;
    const result = validatePatchPanel(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validatePatchPanel({ ...patchPanelInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validatePatchPanel({ ...patchPanelInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires roomId', () => {
    const values: Record<string, unknown> = { ...patchPanelInput };
    delete values.roomId;
    const result = validatePatchPanel(values);
    expect(result.ok).toBe(false);
    expect(result.errors.roomId).toBeDefined();
  });

  it('rejects malformed roomId', () => {
    const result = validatePatchPanel({ ...patchPanelInput, roomId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.roomId).toContain('UUID');
  });

  it('rejects malformed rackId', () => {
    const result = validatePatchPanel({ ...patchPanelInput, rackId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.rackId).toContain('UUID');
  });

  it('rejects unknown panelType', () => {
    const result = validatePatchPanel({ ...patchPanelInput, panelType: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('requires portCount', () => {
    const values: Record<string, unknown> = { ...patchPanelInput };
    delete values.portCount;
    const result = validatePatchPanel(values);
    expect(result.ok).toBe(false);
    expect(result.errors.portCount).toBeDefined();
  });

  it('rejects negative portCount', () => {
    const result = validatePatchPanel({ ...patchPanelInput, portCount: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.portCount).toContain('negative');
  });

  it('rejects label longer than 255', () => {
    const result = validatePatchPanel({ ...patchPanelInput, label: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.label).toContain('Label');
  });

  it('rejects notes longer than 4000', () => {
    const result = validatePatchPanel({ ...patchPanelInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validatePatchPanel({ ...patchPanelInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validatePatchPanel({ ...patchPanelInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validatePatchPanel({ ...patchPanelInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validatePatchPanel({ ...patchPanelInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });
});
