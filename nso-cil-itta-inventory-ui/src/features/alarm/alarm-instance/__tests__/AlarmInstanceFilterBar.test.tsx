import { fireEvent, render, screen } from '@testing-library/react';
import { describe, expect, it, vi } from 'vitest';
import { AlarmInstanceFilterBar } from '../AlarmInstanceFilterBar';
import type { AlarmInstanceFilter } from '../types';

function setup(value: AlarmInstanceFilter = {}) {
  const onChange = vi.fn();
  const onReset = vi.fn();
  render(<AlarmInstanceFilterBar value={value} onChange={onChange} onReset={onReset} />);
  return { onChange, onReset };
}

const CASES: [string, string, Record<string, unknown>][] = [
    ['Code filter', 'abc', { code: 'abc' }],
    ['Name filter', 'abc', { name: 'abc' }],
    ['External Ref filter', 'abc', { externalRef: 'abc' }],
    ['Definition Id filter', '00000000-0000-4000-8000-000000000011', { definitionId: '00000000-0000-4000-8000-000000000011' }],
    ['Source Ref filter', 'abc', { sourceRef: 'abc' }],
    ['Raised At from', '2026-01-01', { raisedAtFrom: '2026-01-01' }],
    ['Cleared At from', '2026-01-01', { clearedAtFrom: '2026-01-01' }],
    ['Acked By filter', 'abc', { ackedBy: 'abc' }],
    ['Effective From from', '2026-01-01', { effectiveFromFrom: '2026-01-01' }],
    ['Effective To from', '2026-01-01', { effectiveToFrom: '2026-01-01' }],
    ['Labels filter', 'abc', { labels: 'abc' }],
    ['Source System filter', 'abc', { sourceSystem: 'abc' }],
];

describe('AlarmInstanceFilterBar', () => {
  it('is labelled for assistive technology', () => {
    setup();
    expect(screen.getByRole('form', { name: 'Alarm Instance filters' })).toBeInTheDocument();
  });

  it('emits free text search', () => {
    const { onChange } = setup();
    fireEvent.change(screen.getByLabelText('Search Alarm Instance'), { target: { value: 'core' } });
    expect(onChange).toHaveBeenCalledWith(expect.objectContaining({ q: 'core' }));
  });

  it('clears q when emptied', () => {
    const { onChange } = setup({ q: 'core' });
    fireEvent.change(screen.getByLabelText('Search Alarm Instance'), { target: { value: '' } });
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
