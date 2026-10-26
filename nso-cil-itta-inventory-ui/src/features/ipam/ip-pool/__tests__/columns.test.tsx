import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { ipPoolColumns, ipPoolDefaultVisible } from '../columns';
import { ipPoolFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['subnetId', 'Subnet Id'],
  ['rangeStart', 'Range Start'],
  ['rangeEnd', 'Range End'],
  ['poolType', 'Pool Type'],
  ['leaseSeconds', 'Lease Seconds'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
];

describe('ipPool columns', () => {
  it('shows code, name and status by default', () => {
    expect(ipPoolDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(ipPoolColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = ipPoolColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(ipPoolFixture)}</MemoryRouter>);
  });
});
