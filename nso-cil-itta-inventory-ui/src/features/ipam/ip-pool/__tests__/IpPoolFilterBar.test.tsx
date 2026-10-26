import { fireEvent, render, screen } from '@testing-library/react';
import { describe, expect, it, vi } from 'vitest';
import { IpPoolFilterBar } from '../IpPoolFilterBar';
import type { IpPoolFilter } from '../types';

function setup(value: IpPoolFilter = {}) {
  const onChange = vi.fn();
  const onReset = vi.fn();
  render(<IpPoolFilterBar value={value} onChange={onChange} onReset={onReset} />);
  return { onChange, onReset };
}

const CASES: [string, string, Record<string, unknown>][] = [
    ['Code filter', 'abc', { code: 'abc' }],
    ['Name filter', 'abc', { name: 'abc' }],
    ['External Ref filter', 'abc', { externalRef: 'abc' }],
    ['Subnet Id filter', '00000000-0000-4000-8000-000000000011', { subnetId: '00000000-0000-4000-8000-000000000011' }],
    ['Range Start filter', 'abc', { rangeStart: 'abc' }],
    ['Range End filter', 'abc', { rangeEnd: 'abc' }],
    ['Lease Seconds minimum', '5', { leaseSecondsMin: 5 }],
    ['Effective From from', '2026-01-01', { effectiveFromFrom: '2026-01-01' }],
    ['Effective To from', '2026-01-01', { effectiveToFrom: '2026-01-01' }],
    ['Labels filter', 'abc', { labels: 'abc' }],
    ['Source System filter', 'abc', { sourceSystem: 'abc' }],
];

describe('IpPoolFilterBar', () => {
  it('is labelled for assistive technology', () => {
    setup();
    expect(screen.getByRole('form', { name: 'Ip Pool filters' })).toBeInTheDocument();
  });

  it('emits free text search', () => {
    const { onChange } = setup();
    fireEvent.change(screen.getByLabelText('Search Ip Pool'), { target: { value: 'core' } });
    expect(onChange).toHaveBeenCalledWith(expect.objectContaining({ q: 'core' }));
  });

  it('clears q when emptied', () => {
    const { onChange } = setup({ q: 'core' });
    fireEvent.change(screen.getByLabelText('Search Ip Pool'), { target: { value: '' } });
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
