import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { powerFeedColumns, powerFeedDefaultVisible } from '../columns';
import { powerFeedFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['roomId', 'Room Id'],
  ['feedType', 'Feed Type'],
  ['voltage', 'Voltage'],
  ['amperage', 'Amperage'],
  ['phase', 'Phase'],
  ['breakerLabel', 'Breaker Label'],
  ['redundancyGroup', 'Redundancy Group'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
];

describe('powerFeed columns', () => {
  it('shows code, name and status by default', () => {
    expect(powerFeedDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(powerFeedColumns).toHaveLength(17);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = powerFeedColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(powerFeedFixture)}</MemoryRouter>);
  });
});
