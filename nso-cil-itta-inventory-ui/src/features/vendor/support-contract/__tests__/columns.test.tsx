import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { supportContractColumns, supportContractDefaultVisible } from '../columns';
import { supportContractFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['vendorId', 'Vendor Id'],
  ['contractNumber', 'Contract Number'],
  ['startDate', 'Start Date'],
  ['endDate', 'End Date'],
  ['coverageLevel', 'Coverage Level'],
  ['annualCost', 'Annual Cost'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
];

describe('supportContract columns', () => {
  it('shows code, name and status by default', () => {
    expect(supportContractDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(supportContractColumns).toHaveLength(16);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = supportContractColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(supportContractFixture)}</MemoryRouter>);
  });
});
