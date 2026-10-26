import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { customerContactColumns, customerContactDefaultVisible } from '../columns';
import { customerContactFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['customerId', 'Customer Id'],
  ['fullName', 'Full Name'],
  ['email', 'Email'],
  ['phone', 'Phone'],
  ['contactRole', 'Contact Role'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['owner', 'Owner'],
];

describe('customerContact columns', () => {
  it('shows code, name and status by default', () => {
    expect(customerContactDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(customerContactColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = customerContactColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(customerContactFixture)}</MemoryRouter>);
  });
});
