import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { vrfInstanceColumns, vrfInstanceDefaultVisible } from '../columns';
import { vrfInstanceFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['vrfName', 'Vrf Name'],
  ['routeDistinguisher', 'Route Distinguisher'],
  ['importTargets', 'Import Targets'],
  ['exportTargets', 'Export Targets'],
  ['deviceId', 'Device Id'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['owner', 'Owner'],
];

describe('vrfInstance columns', () => {
  it('shows code, name and status by default', () => {
    expect(vrfInstanceDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(vrfInstanceColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = vrfInstanceColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(vrfInstanceFixture)}</MemoryRouter>);
  });
});
