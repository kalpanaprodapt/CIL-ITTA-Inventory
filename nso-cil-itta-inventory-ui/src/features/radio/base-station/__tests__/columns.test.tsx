import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { baseStationColumns, baseStationDefaultVisible } from '../columns';
import { baseStationFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['siteId', 'Site Id'],
  ['enbId', 'Enb Id'],
  ['technology', 'Technology'],
  ['sectorCount', 'Sector Count'],
  ['cellCount', 'Cell Count'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['owner', 'Owner'],
];

describe('baseStation columns', () => {
  it('shows code, name and status by default', () => {
    expect(baseStationDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(baseStationColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = baseStationColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(baseStationFixture)}</MemoryRouter>);
  });
});
