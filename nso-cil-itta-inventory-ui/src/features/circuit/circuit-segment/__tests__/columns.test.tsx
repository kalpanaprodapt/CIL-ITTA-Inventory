import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { circuitSegmentColumns, circuitSegmentDefaultVisible } from '../columns';
import { circuitSegmentFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['circuitId', 'Circuit Id'],
  ['segmentOrder', 'Segment Order'],
  ['aTerminationId', 'A Termination Id'],
  ['zTerminationId', 'Z Termination Id'],
  ['latencyMs', 'Latency Ms'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
];

describe('circuitSegment columns', () => {
  it('shows code, name and status by default', () => {
    expect(circuitSegmentDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(circuitSegmentColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = circuitSegmentColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(circuitSegmentFixture)}</MemoryRouter>);
  });
});
