import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { licensePoolColumns, licensePoolDefaultVisible } from '../columns';
import { licensePoolFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['vendorId', 'Vendor Id'],
  ['poolName', 'Pool Name'],
  ['totalSeats', 'Total Seats'],
  ['usedSeats', 'Used Seats'],
  ['renewalDate', 'Renewal Date'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
];

describe('licensePool columns', () => {
  it('shows code, name and status by default', () => {
    expect(licensePoolDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(licensePoolColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = licensePoolColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(licensePoolFixture)}</MemoryRouter>);
  });
});
