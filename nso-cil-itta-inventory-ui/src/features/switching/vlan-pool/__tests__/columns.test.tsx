import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { vlanPoolColumns, vlanPoolDefaultVisible } from '../columns';
import { vlanPoolFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['poolName', 'Pool Name'],
  ['rangeStart', 'Range Start'],
  ['rangeEnd', 'Range End'],
  ['allocated', 'Allocated'],
  ['scope', 'Scope'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
];

describe('vlanPool columns', () => {
  it('shows code, name and status by default', () => {
    expect(vlanPoolDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(vlanPoolColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = vlanPoolColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(vlanPoolFixture)}</MemoryRouter>);
  });
});
