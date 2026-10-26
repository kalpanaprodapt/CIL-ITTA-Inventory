import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { circuitColumns, circuitDefaultVisible } from '../columns';
import { circuitFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['circuitId', 'Circuit Id'],
  ['circuitType', 'Circuit Type'],
  ['bandwidthMbps', 'Bandwidth Mbps'],
  ['providerId', 'Provider Id'],
  ['aSiteId', 'A Site Id'],
  ['zSiteId', 'Z Site Id'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['owner', 'Owner'],
];

describe('circuit columns', () => {
  it('shows code, name and status by default', () => {
    expect(circuitDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(circuitColumns).toHaveLength(16);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = circuitColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(circuitFixture)}</MemoryRouter>);
  });
});
