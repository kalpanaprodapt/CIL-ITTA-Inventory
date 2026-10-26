import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { softwareImageColumns, softwareImageDefaultVisible } from '../columns';
import { softwareImageFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['vendorId', 'Vendor Id'],
  ['imageName', 'Image Name'],
  ['sizeMb', 'Size Mb'],
  ['checksum', 'Checksum'],
  ['platform', 'Platform'],
  ['storagePath', 'Storage Path'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
];

describe('softwareImage columns', () => {
  it('shows code, name and status by default', () => {
    expect(softwareImageDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(softwareImageColumns).toHaveLength(16);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = softwareImageColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(softwareImageFixture)}</MemoryRouter>);
  });
});
