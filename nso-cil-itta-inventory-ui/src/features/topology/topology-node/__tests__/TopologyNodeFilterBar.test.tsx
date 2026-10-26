import { fireEvent, render, screen } from '@testing-library/react';
import { describe, expect, it, vi } from 'vitest';
import { TopologyNodeFilterBar } from '../TopologyNodeFilterBar';
import type { TopologyNodeFilter } from '../types';

function setup(value: TopologyNodeFilter = {}) {
  const onChange = vi.fn();
  const onReset = vi.fn();
  render(<TopologyNodeFilterBar value={value} onChange={onChange} onReset={onReset} />);
  return { onChange, onReset };
}

const CASES: [string, string, Record<string, unknown>][] = [
    ['Code filter', 'abc', { code: 'abc' }],
    ['Name filter', 'abc', { name: 'abc' }],
    ['External Ref filter', 'abc', { externalRef: 'abc' }],
    ['View Id filter', '00000000-0000-4000-8000-000000000011', { viewId: '00000000-0000-4000-8000-000000000011' }],
    ['Entity Ref filter', 'abc', { entityRef: 'abc' }],
    ['Node Type filter', 'abc', { nodeType: 'abc' }],
    ['X Pos minimum', '5', { xPosMin: 5 }],
    ['Y Pos minimum', '5', { yPosMin: 5 }],
    ['Layer minimum', '5', { layerMin: 5 }],
    ['Cost Center filter', 'abc', { costCenter: 'abc' }],
    ['Effective From from', '2026-01-01', { effectiveFromFrom: '2026-01-01' }],
    ['Effective To from', '2026-01-01', { effectiveToFrom: '2026-01-01' }],
];

describe('TopologyNodeFilterBar', () => {
  it('is labelled for assistive technology', () => {
    setup();
    expect(screen.getByRole('form', { name: 'Topology Node filters' })).toBeInTheDocument();
  });

  it('emits free text search', () => {
    const { onChange } = setup();
    fireEvent.change(screen.getByLabelText('Search Topology Node'), { target: { value: 'core' } });
    expect(onChange).toHaveBeenCalledWith(expect.objectContaining({ q: 'core' }));
  });

  it('clears q when emptied', () => {
    const { onChange } = setup({ q: 'core' });
    fireEvent.change(screen.getByLabelText('Search Topology Node'), { target: { value: '' } });
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
