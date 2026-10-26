import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { subInterfaceColumns, subInterfaceDefaultVisible } from '../columns';
import { subInterfaceFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['interfaceId', 'Interface Id'],
  ['vlanTag', 'Vlan Tag'],
  ['encapsulation', 'Encapsulation'],
  ['ipv4Address', 'Ipv4 Address'],
  ['ipv6Address', 'Ipv6 Address'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
];

describe('subInterface columns', () => {
  it('shows code, name and status by default', () => {
    expect(subInterfaceDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(subInterfaceColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = subInterfaceColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(subInterfaceFixture)}</MemoryRouter>);
  });
});
