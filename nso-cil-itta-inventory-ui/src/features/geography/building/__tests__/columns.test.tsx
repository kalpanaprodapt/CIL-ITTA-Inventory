import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { buildingColumns, buildingDefaultVisible } from '../columns';
import { buildingFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['siteId', 'Site Id'],
  ['floors', 'Floors'],
  ['yearBuilt', 'Year Built'],
  ['ownership', 'Ownership'],
  ['leaseExpiry', 'Lease Expiry'],
  ['securityLevel', 'Security Level'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
];

describe('building columns', () => {
  it('shows code, name and status by default', () => {
    expect(buildingDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(buildingColumns).toHaveLength(16);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = buildingColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(buildingFixture)}</MemoryRouter>);
  });
});
