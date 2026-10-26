import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { amplifierColumns, amplifierDefaultVisible } from '../columns';
import { amplifierFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['systemId', 'System Id'],
  ['ampType', 'Amp Type'],
  ['gainDb', 'Gain Db'],
  ['outputPowerDbm', 'Output Power Dbm'],
  ['noiseFigureDb', 'Noise Figure Db'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
];

describe('amplifier columns', () => {
  it('shows code, name and status by default', () => {
    expect(amplifierDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(amplifierColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = amplifierColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(amplifierFixture)}</MemoryRouter>);
  });
});
