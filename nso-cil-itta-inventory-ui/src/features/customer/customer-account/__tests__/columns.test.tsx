import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { customerAccountColumns, customerAccountDefaultVisible } from '../columns';
import { customerAccountFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['customerId', 'Customer Id'],
  ['accountNumber', 'Account Number'],
  ['billingCycle', 'Billing Cycle'],
  ['currency', 'Currency'],
  ['creditLimit', 'Credit Limit'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
];

describe('customerAccount columns', () => {
  it('shows code, name and status by default', () => {
    expect(customerAccountDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(customerAccountColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = customerAccountColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(customerAccountFixture)}</MemoryRouter>);
  });
});
