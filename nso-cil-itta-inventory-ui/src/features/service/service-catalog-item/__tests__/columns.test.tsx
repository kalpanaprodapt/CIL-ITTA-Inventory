import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { serviceCatalogItemColumns, serviceCatalogItemDefaultVisible } from '../columns';
import { serviceCatalogItemFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['itemName', 'Item Name'],
  ['family', 'Family'],
  ['basePrice', 'Base Price'],
  ['billingUnit', 'Billing Unit'],
  ['orderable', 'Orderable'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
];

describe('serviceCatalogItem columns', () => {
  it('shows code, name and status by default', () => {
    expect(serviceCatalogItemDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(serviceCatalogItemColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = serviceCatalogItemColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(serviceCatalogItemFixture)}</MemoryRouter>);
  });
});
