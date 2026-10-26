import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { maintenanceWindowColumns, maintenanceWindowDefaultVisible } from '../columns';
import { maintenanceWindowFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['windowName', 'Window Name'],
  ['startsAt', 'Starts At'],
  ['endsAt', 'Ends At'],
  ['scope', 'Scope'],
  ['impactLevel', 'Impact Level'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
];

describe('maintenanceWindow columns', () => {
  it('shows code, name and status by default', () => {
    expect(maintenanceWindowDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(maintenanceWindowColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = maintenanceWindowColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(maintenanceWindowFixture)}</MemoryRouter>);
  });
});
