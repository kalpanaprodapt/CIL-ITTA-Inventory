import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { cityColumns, cityDefaultVisible } from '../columns';
import { cityFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['regionId', 'Region Id'],
  ['postalPrefix', 'Postal Prefix'],
  ['population', 'Population'],
  ['latitude', 'Latitude'],
  ['longitude', 'Longitude'],
  ['metroArea', 'Metro Area'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
];

describe('city columns', () => {
  it('shows code, name and status by default', () => {
    expect(cityDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(cityColumns).toHaveLength(16);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = cityColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(cityFixture)}</MemoryRouter>);
  });
});
