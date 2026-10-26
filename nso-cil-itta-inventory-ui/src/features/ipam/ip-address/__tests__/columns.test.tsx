import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { ipAddressColumns, ipAddressDefaultVisible } from '../columns';
import { ipAddressFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['subnetId', 'Subnet Id'],
  ['address', 'Address'],
  ['assignedToId', 'Assigned To Id'],
  ['assignmentType', 'Assignment Type'],
  ['dnsName', 'Dns Name'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
];

describe('ipAddress columns', () => {
  it('shows code, name and status by default', () => {
    expect(ipAddressDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(ipAddressColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = ipAddressColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(ipAddressFixture)}</MemoryRouter>);
  });
});
