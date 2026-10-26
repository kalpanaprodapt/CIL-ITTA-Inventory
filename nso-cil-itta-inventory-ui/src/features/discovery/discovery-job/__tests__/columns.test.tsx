import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { discoveryJobColumns, discoveryJobDefaultVisible } from '../columns';
import { discoveryJobFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['profileId', 'Profile Id'],
  ['startedAt', 'Started At'],
  ['finishedAt', 'Finished At'],
  ['jobState', 'Job State'],
  ['assetsFound', 'Assets Found'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
];

describe('discoveryJob columns', () => {
  it('shows code, name and status by default', () => {
    expect(discoveryJobDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(discoveryJobColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = discoveryJobColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(discoveryJobFixture)}</MemoryRouter>);
  });
});
