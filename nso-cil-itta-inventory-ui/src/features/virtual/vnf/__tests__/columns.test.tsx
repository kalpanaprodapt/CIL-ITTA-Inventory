import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { vnfColumns, vnfDefaultVisible } from '../columns';
import { vnfFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['vnfName', 'Vnf Name'],
  ['vnfType', 'Vnf Type'],
  ['flavor', 'Flavor'],
  ['vcpu', 'Vcpu'],
  ['memoryGb', 'Memory Gb'],
  ['instanceCount', 'Instance Count'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
];

describe('vnf columns', () => {
  it('shows code, name and status by default', () => {
    expect(vnfDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(vnfColumns).toHaveLength(16);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = vnfColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(vnfFixture)}</MemoryRouter>);
  });
});
