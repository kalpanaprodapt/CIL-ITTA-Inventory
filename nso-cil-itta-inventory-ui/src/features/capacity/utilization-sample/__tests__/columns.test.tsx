import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { utilizationSampleColumns, utilizationSampleDefaultVisible } from '../columns';
import { utilizationSampleFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['targetRef', 'Target Ref'],
  ['metric', 'Metric'],
  ['sampledAt', 'Sampled At'],
  ['valuePct', 'Value Pct'],
  ['peak', 'Peak'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
];

describe('utilizationSample columns', () => {
  it('shows code, name and status by default', () => {
    expect(utilizationSampleDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(utilizationSampleColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = utilizationSampleColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(utilizationSampleFixture)}</MemoryRouter>);
  });
});
