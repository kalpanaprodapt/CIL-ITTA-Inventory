import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { slaProfileColumns, slaProfileDefaultVisible } from '../columns';
import { slaProfileFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['profileName', 'Profile Name'],
  ['availabilityPct', 'Availability Pct'],
  ['mttrMinutes', 'Mttr Minutes'],
  ['latencyMs', 'Latency Ms'],
  ['jitterMs', 'Jitter Ms'],
  ['creditPolicy', 'Credit Policy'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
];

describe('slaProfile columns', () => {
  it('shows code, name and status by default', () => {
    expect(slaProfileDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(slaProfileColumns).toHaveLength(16);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = slaProfileColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(slaProfileFixture)}</MemoryRouter>);
  });
});
