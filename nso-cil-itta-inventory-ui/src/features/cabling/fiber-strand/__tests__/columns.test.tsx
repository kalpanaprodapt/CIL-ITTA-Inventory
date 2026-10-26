import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { fiberStrandColumns, fiberStrandDefaultVisible } from '../columns';
import { fiberStrandFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['cableId', 'Cable Id'],
  ['strandNumber', 'Strand Number'],
  ['bufferTube', 'Buffer Tube'],
  ['tubeColor', 'Tube Color'],
  ['attenuationDb', 'Attenuation Db'],
  ['inUse', 'In Use'],
  ['region', 'Region'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
];

describe('fiberStrand columns', () => {
  it('shows code, name and status by default', () => {
    expect(fiberStrandDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(fiberStrandColumns).toHaveLength(16);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = fiberStrandColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(fiberStrandFixture)}</MemoryRouter>);
  });
});
