import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { floorColumns, floorDefaultVisible } from '../columns';
import { floorFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['buildingId', 'Building Id'],
  ['level', 'Level'],
  ['areaSqm', 'Area Sqm'],
  ['loadCapacityKg', 'Load Capacity Kg'],
  ['hasRaisedFloor', 'Has Raised Floor'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
];

describe('floor columns', () => {
  it('shows code, name and status by default', () => {
    expect(floorDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(floorColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = floorColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(floorFixture)}</MemoryRouter>);
  });
});
