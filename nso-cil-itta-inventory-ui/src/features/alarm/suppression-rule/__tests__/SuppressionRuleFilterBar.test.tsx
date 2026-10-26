import { fireEvent, render, screen } from '@testing-library/react';
import { describe, expect, it, vi } from 'vitest';
import { SuppressionRuleFilterBar } from '../SuppressionRuleFilterBar';
import type { SuppressionRuleFilter } from '../types';

function setup(value: SuppressionRuleFilter = {}) {
  const onChange = vi.fn();
  const onReset = vi.fn();
  render(<SuppressionRuleFilterBar value={value} onChange={onChange} onReset={onReset} />);
  return { onChange, onReset };
}

const CASES: [string, string, Record<string, unknown>][] = [
    ['Code filter', 'abc', { code: 'abc' }],
    ['Name filter', 'abc', { name: 'abc' }],
    ['External Ref filter', 'abc', { externalRef: 'abc' }],
    ['Rule Name filter', 'abc', { ruleName: 'abc' }],
    ['Starts At from', '2026-01-01', { startsAtFrom: '2026-01-01' }],
    ['Ends At from', '2026-01-01', { endsAtFrom: '2026-01-01' }],
    ['Reason filter', 'abc', { reason: 'abc' }],
    ['Source System filter', 'abc', { sourceSystem: 'abc' }],
    ['Region filter', 'abc', { region: 'abc' }],
    ['Owner filter', 'abc', { owner: 'abc' }],
    ['Cost Center filter', 'abc', { costCenter: 'abc' }],
];

describe('SuppressionRuleFilterBar', () => {
  it('is labelled for assistive technology', () => {
    setup();
    expect(screen.getByRole('form', { name: 'Suppression Rule filters' })).toBeInTheDocument();
  });

  it('emits free text search', () => {
    const { onChange } = setup();
    fireEvent.change(screen.getByLabelText('Search Suppression Rule'), { target: { value: 'core' } });
    expect(onChange).toHaveBeenCalledWith(expect.objectContaining({ q: 'core' }));
  });

  it('clears q when emptied', () => {
    const { onChange } = setup({ q: 'core' });
    fireEvent.change(screen.getByLabelText('Search Suppression Rule'), { target: { value: '' } });
    expect(onChange).toHaveBeenCalledWith(expect.objectContaining({ q: undefined }));
  });

  it('offers all lifecycle statuses', () => {
    setup();
    expect((screen.getByLabelText('Status filter') as HTMLSelectElement).options).toHaveLength(4);
  });

  it('calls onReset', () => {
    const { onReset } = setup({ q: 'x' });
    fireEvent.click(screen.getByRole('button', { name: 'Reset filters' }));
    expect(onReset).toHaveBeenCalled();
  });

  it.each(CASES)('emits a patch when %s changes', (label, value, patch) => {
    const { onChange } = setup();
    fireEvent.change(screen.getByLabelText(label), { target: { value } });
    expect(onChange).toHaveBeenCalledWith(expect.objectContaining(patch));
  });
});
