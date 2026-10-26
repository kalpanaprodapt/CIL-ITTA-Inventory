import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { portColumns, portDefaultVisible } from '../columns';
import { portFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['deviceId', 'Device Id'],
  ['cardId', 'Card Id'],
  ['portNumber', 'Port Number'],
  ['speedMbps', 'Speed Mbps'],
  ['mediaType', 'Media Type'],
  ['adminState', 'Admin State'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
];

describe('port columns', () => {
  it('shows code, name and status by default', () => {
    expect(portDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(portColumns).toHaveLength(16);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = portColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(portFixture)}</MemoryRouter>);
  });
});
