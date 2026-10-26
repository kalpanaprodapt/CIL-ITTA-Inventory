import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { interfaceColumns, interfaceDefaultVisible } from '../columns';
import { interfaceFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['portId', 'Port Id'],
  ['ifName', 'If Name'],
  ['ifIndex', 'If Index'],
  ['macAddress', 'Mac Address'],
  ['mtu', 'Mtu'],
  ['operState', 'Oper State'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
];

describe('interface columns', () => {
  it('shows code, name and status by default', () => {
    expect(interfaceDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(interfaceColumns).toHaveLength(16);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = interfaceColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(interfaceFixture)}</MemoryRouter>);
  });
});
