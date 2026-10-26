import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { tunnelEndpointColumns, tunnelEndpointDefaultVisible } from '../columns';
import { tunnelEndpointFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['deviceId', 'Device Id'],
  ['tunnelName', 'Tunnel Name'],
  ['tunnelType', 'Tunnel Type'],
  ['sourceIp', 'Source Ip'],
  ['destinationIp', 'Destination Ip'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
];

describe('tunnelEndpoint columns', () => {
  it('shows code, name and status by default', () => {
    expect(tunnelEndpointDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(tunnelEndpointColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = tunnelEndpointColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(tunnelEndpointFixture)}</MemoryRouter>);
  });
});
