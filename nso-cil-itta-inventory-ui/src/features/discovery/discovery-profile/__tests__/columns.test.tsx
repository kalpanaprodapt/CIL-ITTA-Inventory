import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { discoveryProfileColumns, discoveryProfileDefaultVisible } from '../columns';
import { discoveryProfileFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['profileName', 'Profile Name'],
  ['protocol', 'Protocol'],
  ['credentialRef', 'Credential Ref'],
  ['scopeCidr', 'Scope Cidr'],
  ['scheduleCron', 'Schedule Cron'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
];

describe('discoveryProfile columns', () => {
  it('shows code, name and status by default', () => {
    expect(discoveryProfileDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(discoveryProfileColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = discoveryProfileColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(discoveryProfileFixture)}</MemoryRouter>);
  });
});
