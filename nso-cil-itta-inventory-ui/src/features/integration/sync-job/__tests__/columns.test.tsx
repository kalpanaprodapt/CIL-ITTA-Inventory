import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { syncJobColumns, syncJobDefaultVisible } from '../columns';
import { syncJobFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['endpointId', 'Endpoint Id'],
  ['direction', 'Direction'],
  ['scheduleCron', 'Schedule Cron'],
  ['lastRunAt', 'Last Run At'],
  ['lastStatus', 'Last Status'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
];

describe('syncJob columns', () => {
  it('shows code, name and status by default', () => {
    expect(syncJobDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(syncJobColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = syncJobColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(syncJobFixture)}</MemoryRouter>);
  });
});
