import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { rackUnitColumns, rackUnitDefaultVisible } from '../columns';
import { rackUnitFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['rackId', 'Rack Id'],
  ['unitNumber', 'Unit Number'],
  ['occupied', 'Occupied'],
  ['mountedDeviceId', 'Mounted Device Id'],
  ['face', 'Face'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
];

describe('rackUnit columns', () => {
  it('shows code, name and status by default', () => {
    expect(rackUnitDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(rackUnitColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = rackUnitColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(rackUnitFixture)}</MemoryRouter>);
  });
});
