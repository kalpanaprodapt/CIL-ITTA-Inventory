import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { rackColumns, rackDefaultVisible } from '../columns';
import { rackFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['roomId', 'Room Id'],
  ['rackUnits', 'Rack Units'],
  ['widthMm', 'Width Mm'],
  ['depthMm', 'Depth Mm'],
  ['maxWeightKg', 'Max Weight Kg'],
  ['rowLabel', 'Row Label'],
  ['position', 'Position'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['owner', 'Owner'],
];

describe('rack columns', () => {
  it('shows code, name and status by default', () => {
    expect(rackDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(rackColumns).toHaveLength(17);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = rackColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(rackFixture)}</MemoryRouter>);
  });
});
