import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { wdmSystemColumns, wdmSystemDefaultVisible } from '../columns';
import { wdmSystemFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['systemName', 'System Name'],
  ['channelCapacity', 'Channel Capacity'],
  ['band', 'Band'],
  ['spanCount', 'Span Count'],
  ['vendorId', 'Vendor Id'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
];

describe('wdmSystem columns', () => {
  it('shows code, name and status by default', () => {
    expect(wdmSystemDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(wdmSystemColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = wdmSystemColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(wdmSystemFixture)}</MemoryRouter>);
  });
});
