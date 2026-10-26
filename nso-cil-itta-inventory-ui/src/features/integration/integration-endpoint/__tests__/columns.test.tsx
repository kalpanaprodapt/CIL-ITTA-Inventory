import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { integrationEndpointColumns, integrationEndpointDefaultVisible } from '../columns';
import { integrationEndpointFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['endpointName', 'Endpoint Name'],
  ['targetSystem', 'Target System'],
  ['baseUrl', 'Base Url'],
  ['authType', 'Auth Type'],
  ['timeoutMs', 'Timeout Ms'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
];

describe('integrationEndpoint columns', () => {
  it('shows code, name and status by default', () => {
    expect(integrationEndpointDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(integrationEndpointColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = integrationEndpointColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(integrationEndpointFixture)}</MemoryRouter>);
  });
});
