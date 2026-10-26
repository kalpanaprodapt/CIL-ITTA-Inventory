import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { mplsLspColumns, mplsLspDefaultVisible } from '../columns';
import { mplsLspFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['lspName', 'Lsp Name'],
  ['headEndId', 'Head End Id'],
  ['tailEndId', 'Tail End Id'],
  ['bandwidthMbps', 'Bandwidth Mbps'],
  ['setupPriority', 'Setup Priority'],
  ['pathType', 'Path Type'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
];

describe('mplsLsp columns', () => {
  it('shows code, name and status by default', () => {
    expect(mplsLspDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(mplsLspColumns).toHaveLength(16);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = mplsLspColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(mplsLspFixture)}</MemoryRouter>);
  });
});
