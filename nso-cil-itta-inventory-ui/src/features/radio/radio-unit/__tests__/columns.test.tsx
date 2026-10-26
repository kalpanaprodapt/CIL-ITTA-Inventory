import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { radioUnitColumns, radioUnitDefaultVisible } from '../columns';
import { radioUnitFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['siteId', 'Site Id'],
  ['unitType', 'Unit Type'],
  ['bandMhz', 'Band Mhz'],
  ['txPowerDbm', 'Tx Power Dbm'],
  ['serialNumber', 'Serial Number'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
];

describe('radioUnit columns', () => {
  it('shows code, name and status by default', () => {
    expect(radioUnitDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(radioUnitColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = radioUnitColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(radioUnitFixture)}</MemoryRouter>);
  });
});
