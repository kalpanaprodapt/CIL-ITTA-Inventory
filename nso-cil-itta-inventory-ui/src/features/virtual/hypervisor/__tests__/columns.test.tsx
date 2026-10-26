import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { hypervisorColumns, hypervisorDefaultVisible } from '../columns';
import { hypervisorFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['hostDeviceId', 'Host Device Id'],
  ['hypervisorType', 'Hypervisor Type'],
  ['hvVersion', 'Hv Version'],
  ['vcpuCapacity', 'Vcpu Capacity'],
  ['memoryGbCapacity', 'Memory Gb Capacity'],
  ['region', 'Region'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
];

describe('hypervisor columns', () => {
  it('shows code, name and status by default', () => {
    expect(hypervisorDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(hypervisorColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = hypervisorColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(hypervisorFixture)}</MemoryRouter>);
  });
});
