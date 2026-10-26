import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { siteColumns, siteDefaultVisible } from '../columns';
import { siteFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['cityId', 'City Id'],
  ['siteType', 'Site Type'],
  ['address', 'Address'],
  ['latitude', 'Latitude'],
  ['longitude', 'Longitude'],
  ['powerKw', 'Power Kw'],
  ['accessHours', 'Access Hours'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
];

describe('site columns', () => {
  it('shows code, name and status by default', () => {
    expect(siteDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(siteColumns).toHaveLength(17);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = siteColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(siteFixture)}</MemoryRouter>);
  });
});
