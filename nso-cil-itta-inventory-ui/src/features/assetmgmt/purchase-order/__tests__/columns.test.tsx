import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { purchaseOrderColumns, purchaseOrderDefaultVisible } from '../columns';
import { purchaseOrderFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['poNumber', 'Po Number'],
  ['vendorId', 'Vendor Id'],
  ['orderedOn', 'Ordered On'],
  ['totalAmount', 'Total Amount'],
  ['currency', 'Currency'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
];

describe('purchaseOrder columns', () => {
  it('shows code, name and status by default', () => {
    expect(purchaseOrderDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(purchaseOrderColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = purchaseOrderColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(purchaseOrderFixture)}</MemoryRouter>);
  });
});
