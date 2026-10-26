import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { serviceEndpointColumns, serviceEndpointDefaultVisible } from '../columns';
import { serviceEndpointFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['serviceId', 'Service Id'],
  ['siteId', 'Site Id'],
  ['portId', 'Port Id'],
  ['endpointRole', 'Endpoint Role'],
  ['handoff', 'Handoff'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
];

describe('serviceEndpoint columns', () => {
  it('shows code, name and status by default', () => {
    expect(serviceEndpointDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(serviceEndpointColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = serviceEndpointColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(serviceEndpointFixture)}</MemoryRouter>);
  });
});
