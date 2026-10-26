import { fireEvent, render, screen } from '@testing-library/react';
import { describe, expect, it, vi } from 'vitest';
import { ChassisFilterBar } from '../ChassisFilterBar';
import type { ChassisFilter } from '../types';

function setup(value: ChassisFilter = {}) {
  const onChange = vi.fn();
  const onReset = vi.fn();
  render(<ChassisFilterBar value={value} onChange={onChange} onReset={onReset} />);
  return { onChange, onReset };
}

const CASES: [string, string, Record<string, unknown>][] = [
    ['Code filter', 'abc', { code: 'abc' }],
    ['Name filter', 'abc', { name: 'abc' }],
    ['External Ref filter', 'abc', { externalRef: 'abc' }],
    ['Device Id filter', '00000000-0000-4000-8000-000000000011', { deviceId: '00000000-0000-4000-8000-000000000011' }],
    ['Slot Count minimum', '5', { slotCountMin: 5 }],
    ['Serial Number filter', 'abc', { serialNumber: 'abc' }],
    ['Backplane Gbps minimum', '5', { backplaneGbpsMin: 5 }],
    ['Redundancy Mode filter', 'abc', { redundancyMode: 'abc' }],
    ['Source System filter', 'abc', { sourceSystem: 'abc' }],
    ['Region filter', 'abc', { region: 'abc' }],
    ['Owner filter', 'abc', { owner: 'abc' }],
];

describe('ChassisFilterBar', () => {
  it('is labelled for assistive technology', () => {
    setup();
    expect(screen.getByRole('form', { name: 'Chassis filters' })).toBeInTheDocument();
  });

  it('emits free text search', () => {
    const { onChange } = setup();
    fireEvent.change(screen.getByLabelText('Search Chassis'), { target: { value: 'core' } });
    expect(onChange).toHaveBeenCalledWith(expect.objectContaining({ q: 'core' }));
  });

  it('clears q when emptied', () => {
    const { onChange } = setup({ q: 'core' });
    fireEvent.change(screen.getByLabelText('Search Chassis'), { target: { value: '' } });
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
