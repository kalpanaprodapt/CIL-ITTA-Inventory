import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { chassisColumns, chassisDefaultVisible } from '../columns';
import { chassisFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['deviceId', 'Device Id'],
  ['slotCount', 'Slot Count'],
  ['serialNumber', 'Serial Number'],
  ['backplaneGbps', 'Backplane Gbps'],
  ['redundancyMode', 'Redundancy Mode'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
];

describe('chassis columns', () => {
  it('shows code, name and status by default', () => {
    expect(chassisDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(chassisColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = chassisColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(chassisFixture)}</MemoryRouter>);
  });
});
