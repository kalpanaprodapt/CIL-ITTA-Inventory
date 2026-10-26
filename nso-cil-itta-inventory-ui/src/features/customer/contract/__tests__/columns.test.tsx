import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { contractColumns, contractDefaultVisible } from '../columns';
import { contractFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['customerId', 'Customer Id'],
  ['contractNumber', 'Contract Number'],
  ['startDate', 'Start Date'],
  ['endDate', 'End Date'],
  ['termMonths', 'Term Months'],
  ['autoRenew', 'Auto Renew'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
];

describe('contract columns', () => {
  it('shows code, name and status by default', () => {
    expect(contractDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(contractColumns).toHaveLength(16);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = contractColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(contractFixture)}</MemoryRouter>);
  });
});
