import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { networkDeviceColumns, networkDeviceDefaultVisible } from '../columns';
import { networkDeviceFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['hostname', 'Hostname'],
  ['deviceRole', 'Device Role'],
  ['mgmtIp', 'Mgmt Ip'],
  ['serialNumber', 'Serial Number'],
  ['modelId', 'Model Id'],
  ['rackUnitId', 'Rack Unit Id'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
];

describe('networkDevice columns', () => {
  it('shows code, name and status by default', () => {
    expect(networkDeviceDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(networkDeviceColumns).toHaveLength(16);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = networkDeviceColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(networkDeviceFixture)}</MemoryRouter>);
  });
});
