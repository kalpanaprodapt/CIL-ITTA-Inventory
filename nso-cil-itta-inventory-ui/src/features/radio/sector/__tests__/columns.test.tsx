import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { sectorColumns, sectorDefaultVisible } from '../columns';
import { sectorFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['baseStationId', 'Base Station Id'],
  ['sectorNumber', 'Sector Number'],
  ['pci', 'Pci'],
  ['frequencyMhz', 'Frequency Mhz'],
  ['bandwidthMhz', 'Bandwidth Mhz'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
];

describe('sector columns', () => {
  it('shows code, name and status by default', () => {
    expect(sectorDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(sectorColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = sectorColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(sectorFixture)}</MemoryRouter>);
  });
});
