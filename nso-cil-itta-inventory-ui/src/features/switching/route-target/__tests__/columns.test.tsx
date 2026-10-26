import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { routeTargetColumns, routeTargetDefaultVisible } from '../columns';
import { routeTargetFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['asn', 'Asn'],
  ['assignedNumber', 'Assigned Number'],
  ['rtType', 'Rt Type'],
  ['vrfId', 'Vrf Id'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
];

describe('routeTarget columns', () => {
  it('shows code, name and status by default', () => {
    expect(routeTargetDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(routeTargetColumns).toHaveLength(14);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = routeTargetColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(routeTargetFixture)}</MemoryRouter>);
  });
});
