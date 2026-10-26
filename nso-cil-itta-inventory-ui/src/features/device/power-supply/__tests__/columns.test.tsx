import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { powerSupplyColumns, powerSupplyDefaultVisible } from '../columns';
import { powerSupplyFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['chassisId', 'Chassis Id'],
  ['slotNumber', 'Slot Number'],
  ['wattage', 'Wattage'],
  ['feedId', 'Feed Id'],
  ['serialNumber', 'Serial Number'],
  ['efficiency', 'Efficiency'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
];

describe('powerSupply columns', () => {
  it('shows code, name and status by default', () => {
    expect(powerSupplyDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(powerSupplyColumns).toHaveLength(16);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = powerSupplyColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(powerSupplyFixture)}</MemoryRouter>);
  });
});
