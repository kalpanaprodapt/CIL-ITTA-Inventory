import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { lineCardColumns, lineCardDefaultVisible } from '../columns';
import { lineCardFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['chassisId', 'Chassis Id'],
  ['slotNumber', 'Slot Number'],
  ['cardType', 'Card Type'],
  ['portCount', 'Port Count'],
  ['serialNumber', 'Serial Number'],
  ['firmware', 'Firmware'],
  ['region', 'Region'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
];

describe('lineCard columns', () => {
  it('shows code, name and status by default', () => {
    expect(lineCardDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(lineCardColumns).toHaveLength(16);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = lineCardColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(lineCardFixture)}</MemoryRouter>);
  });
});
