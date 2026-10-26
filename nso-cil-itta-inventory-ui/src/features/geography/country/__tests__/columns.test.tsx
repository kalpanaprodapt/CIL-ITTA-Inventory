import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { countryColumns, countryDefaultVisible } from '../columns';
import { countryFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['isoCode', 'Iso Code'],
  ['isoNumeric', 'Iso Numeric'],
  ['dialCode', 'Dial Code'],
  ['currency', 'Currency'],
  ['regulatoryBody', 'Regulatory Body'],
  ['timezone', 'Timezone'],
  ['region', 'Region'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
];

describe('country columns', () => {
  it('shows code, name and status by default', () => {
    expect(countryDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(countryColumns).toHaveLength(16);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = countryColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(countryFixture)}</MemoryRouter>);
  });
});
