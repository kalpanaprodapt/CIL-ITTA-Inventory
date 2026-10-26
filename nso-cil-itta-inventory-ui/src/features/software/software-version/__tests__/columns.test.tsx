import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { softwareVersionColumns, softwareVersionDefaultVisible } from '../columns';
import { softwareVersionFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['imageId', 'Image Id'],
  ['versionString', 'Version String'],
  ['releaseDate', 'Release Date'],
  ['endOfSupport', 'End Of Support'],
  ['releaseTrain', 'Release Train'],
  ['cveCount', 'Cve Count'],
  ['region', 'Region'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
];

describe('softwareVersion columns', () => {
  it('shows code, name and status by default', () => {
    expect(softwareVersionDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(softwareVersionColumns).toHaveLength(16);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = softwareVersionColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(softwareVersionFixture)}</MemoryRouter>);
  });
});
