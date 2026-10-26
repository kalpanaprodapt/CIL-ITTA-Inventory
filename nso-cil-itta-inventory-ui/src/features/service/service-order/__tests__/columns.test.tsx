import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { serviceOrderColumns, serviceOrderDefaultVisible } from '../columns';
import { serviceOrderFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['customerId', 'Customer Id'],
  ['orderType', 'Order Type'],
  ['requestedOn', 'Requested On'],
  ['targetDate', 'Target Date'],
  ['priority', 'Priority'],
  ['region', 'Region'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
];

describe('serviceOrder columns', () => {
  it('shows code, name and status by default', () => {
    expect(serviceOrderDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(serviceOrderColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = serviceOrderColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(serviceOrderFixture)}</MemoryRouter>);
  });
});
