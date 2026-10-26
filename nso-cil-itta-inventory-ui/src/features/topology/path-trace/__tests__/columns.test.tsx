import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { pathTraceColumns, pathTraceDefaultVisible } from '../columns';
import { pathTraceFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['sourceRef', 'Source Ref'],
  ['targetRef', 'Target Ref'],
  ['hopCount', 'Hop Count'],
  ['totalLatencyMs', 'Total Latency Ms'],
  ['traceMethod', 'Trace Method'],
  ['tracedAt', 'Traced At'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
];

describe('pathTrace columns', () => {
  it('shows code, name and status by default', () => {
    expect(pathTraceDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(pathTraceColumns).toHaveLength(16);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = pathTraceColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(pathTraceFixture)}</MemoryRouter>);
  });
});
