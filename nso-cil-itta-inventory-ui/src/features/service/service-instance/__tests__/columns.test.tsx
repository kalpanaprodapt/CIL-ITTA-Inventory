import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { serviceInstanceColumns, serviceInstanceDefaultVisible } from '../columns';
import { serviceInstanceFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['catalogItemId', 'Catalog Item Id'],
  ['customerId', 'Customer Id'],
  ['activatedOn', 'Activated On'],
  ['bandwidthMbps', 'Bandwidth Mbps'],
  ['serviceState', 'Service State'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
];

describe('serviceInstance columns', () => {
  it('shows code, name and status by default', () => {
    expect(serviceInstanceDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(serviceInstanceColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = serviceInstanceColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(serviceInstanceFixture)}</MemoryRouter>);
  });
});
