import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { linkColumns, linkDefaultVisible } from '../columns';
import { linkFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['aInterfaceId', 'A Interface Id'],
  ['zInterfaceId', 'Z Interface Id'],
  ['linkType', 'Link Type'],
  ['speedMbps', 'Speed Mbps'],
  ['discoveredBy', 'Discovered By'],
  ['region', 'Region'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
];

describe('link columns', () => {
  it('shows code, name and status by default', () => {
    expect(linkDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(linkColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = linkColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(linkFixture)}</MemoryRouter>);
  });
});
