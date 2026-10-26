import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { ipSubnetColumns, ipSubnetDefaultVisible } from '../columns';
import { ipSubnetFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['cidr', 'Cidr'],
  ['ipVersion', 'Ip Version'],
  ['vrfId', 'Vrf Id'],
  ['gateway', 'Gateway'],
  ['vlanId', 'Vlan Id'],
  ['utilizationPct', 'Utilization Pct'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
];

describe('ipSubnet columns', () => {
  it('shows code, name and status by default', () => {
    expect(ipSubnetDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(ipSubnetColumns).toHaveLength(16);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = ipSubnetColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(ipSubnetFixture)}</MemoryRouter>);
  });
});
