import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { antennaColumns, antennaDefaultVisible } from '../columns';
import { antennaFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['sectorId', 'Sector Id'],
  ['model', 'Model'],
  ['heightM', 'Height M'],
  ['azimuthDeg', 'Azimuth Deg'],
  ['tiltDeg', 'Tilt Deg'],
  ['gainDbi', 'Gain Dbi'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
];

describe('antenna columns', () => {
  it('shows code, name and status by default', () => {
    expect(antennaDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(antennaColumns).toHaveLength(16);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = antennaColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(antennaFixture)}</MemoryRouter>);
  });
});
