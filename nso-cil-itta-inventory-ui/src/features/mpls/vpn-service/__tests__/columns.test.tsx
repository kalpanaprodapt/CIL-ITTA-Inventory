import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { vpnServiceColumns, vpnServiceDefaultVisible } from '../columns';
import { vpnServiceFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['vpnName', 'Vpn Name'],
  ['vpnType', 'Vpn Type'],
  ['vrfId', 'Vrf Id'],
  ['customerId', 'Customer Id'],
  ['topology', 'Topology'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
];

describe('vpnService columns', () => {
  it('shows code, name and status by default', () => {
    expect(vpnServiceDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(vpnServiceColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = vpnServiceColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(vpnServiceFixture)}</MemoryRouter>);
  });
});
