import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { circuitProviderColumns, circuitProviderDefaultVisible } from '../columns';
import { circuitProviderFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['providerName', 'Provider Name'],
  ['accountNumber', 'Account Number'],
  ['noc', 'Noc'],
  ['escalationEmail', 'Escalation Email'],
  ['providerType', 'Provider Type'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
];

describe('circuitProvider columns', () => {
  it('shows code, name and status by default', () => {
    expect(circuitProviderDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(circuitProviderColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = circuitProviderColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(circuitProviderFixture)}</MemoryRouter>);
  });
});
