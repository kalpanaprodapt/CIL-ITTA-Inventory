import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { virtualMachineColumns, virtualMachineDefaultVisible } from '../columns';
import { virtualMachineFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['hypervisorId', 'Hypervisor Id'],
  ['vmName', 'Vm Name'],
  ['vcpu', 'Vcpu'],
  ['memoryGb', 'Memory Gb'],
  ['diskGb', 'Disk Gb'],
  ['powerState', 'Power State'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
];

describe('virtualMachine columns', () => {
  it('shows code, name and status by default', () => {
    expect(virtualMachineDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(virtualMachineColumns).toHaveLength(16);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = virtualMachineColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(virtualMachineFixture)}</MemoryRouter>);
  });
});
