import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { backhaulLinkColumns, backhaulLinkDefaultVisible } from '../columns';
import { backhaulLinkFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['baseStationId', 'Base Station Id'],
  ['linkMedium', 'Link Medium'],
  ['capacityMbps', 'Capacity Mbps'],
  ['latencyMs', 'Latency Ms'],
  ['circuitRef', 'Circuit Ref'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
];

describe('backhaulLink columns', () => {
  it('shows code, name and status by default', () => {
    expect(backhaulLinkDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(backhaulLinkColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = backhaulLinkColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(backhaulLinkFixture)}</MemoryRouter>);
  });
});
