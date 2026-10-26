import { fireEvent, render, screen } from '@testing-library/react';
import { describe, expect, it, vi } from 'vitest';
import { AmplifierFilterBar } from '../AmplifierFilterBar';
import type { AmplifierFilter } from '../types';

function setup(value: AmplifierFilter = {}) {
  const onChange = vi.fn();
  const onReset = vi.fn();
  render(<AmplifierFilterBar value={value} onChange={onChange} onReset={onReset} />);
  return { onChange, onReset };
}

const CASES: [string, string, Record<string, unknown>][] = [
    ['Code filter', 'abc', { code: 'abc' }],
    ['Name filter', 'abc', { name: 'abc' }],
    ['External Ref filter', 'abc', { externalRef: 'abc' }],
    ['System Id filter', '00000000-0000-4000-8000-000000000011', { systemId: '00000000-0000-4000-8000-000000000011' }],
    ['Gain Db minimum', '5', { gainDbMin: 5 }],
    ['Output Power Dbm minimum', '5', { outputPowerDbmMin: 5 }],
    ['Noise Figure Db minimum', '5', { noiseFigureDbMin: 5 }],
    ['Source System filter', 'abc', { sourceSystem: 'abc' }],
    ['Region filter', 'abc', { region: 'abc' }],
    ['Owner filter', 'abc', { owner: 'abc' }],
];

describe('AmplifierFilterBar', () => {
  it('is labelled for assistive technology', () => {
    setup();
    expect(screen.getByRole('form', { name: 'Amplifier filters' })).toBeInTheDocument();
  });

  it('emits free text search', () => {
    const { onChange } = setup();
    fireEvent.change(screen.getByLabelText('Search Amplifier'), { target: { value: 'core' } });
    expect(onChange).toHaveBeenCalledWith(expect.objectContaining({ q: 'core' }));
  });

  it('clears q when emptied', () => {
    const { onChange } = setup({ q: 'core' });
    fireEvent.change(screen.getByLabelText('Search Amplifier'), { target: { value: '' } });
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
