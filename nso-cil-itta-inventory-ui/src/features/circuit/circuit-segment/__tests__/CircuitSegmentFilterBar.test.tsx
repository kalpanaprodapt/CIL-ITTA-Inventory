import { fireEvent, render, screen } from '@testing-library/react';
import { describe, expect, it, vi } from 'vitest';
import { CircuitSegmentFilterBar } from '../CircuitSegmentFilterBar';
import type { CircuitSegmentFilter } from '../types';

function setup(value: CircuitSegmentFilter = {}) {
  const onChange = vi.fn();
  const onReset = vi.fn();
  render(<CircuitSegmentFilterBar value={value} onChange={onChange} onReset={onReset} />);
  return { onChange, onReset };
}

const CASES: [string, string, Record<string, unknown>][] = [
    ['Code filter', 'abc', { code: 'abc' }],
    ['Name filter', 'abc', { name: 'abc' }],
    ['External Ref filter', 'abc', { externalRef: 'abc' }],
    ['Circuit Id filter', '00000000-0000-4000-8000-000000000011', { circuitId: '00000000-0000-4000-8000-000000000011' }],
    ['Segment Order minimum', '5', { segmentOrderMin: 5 }],
    ['A Termination Id filter', '00000000-0000-4000-8000-000000000011', { aTerminationId: '00000000-0000-4000-8000-000000000011' }],
    ['Z Termination Id filter', '00000000-0000-4000-8000-000000000011', { zTerminationId: '00000000-0000-4000-8000-000000000011' }],
    ['Latency Ms minimum', '5', { latencyMsMin: 5 }],
    ['Source System filter', 'abc', { sourceSystem: 'abc' }],
    ['Region filter', 'abc', { region: 'abc' }],
    ['Owner filter', 'abc', { owner: 'abc' }],
    ['Cost Center filter', 'abc', { costCenter: 'abc' }],
];

describe('CircuitSegmentFilterBar', () => {
  it('is labelled for assistive technology', () => {
    setup();
    expect(screen.getByRole('form', { name: 'Circuit Segment filters' })).toBeInTheDocument();
  });

  it('emits free text search', () => {
    const { onChange } = setup();
    fireEvent.change(screen.getByLabelText('Search Circuit Segment'), { target: { value: 'core' } });
    expect(onChange).toHaveBeenCalledWith(expect.objectContaining({ q: 'core' }));
  });

  it('clears q when emptied', () => {
    const { onChange } = setup({ q: 'core' });
    fireEvent.change(screen.getByLabelText('Search Circuit Segment'), { target: { value: '' } });
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
