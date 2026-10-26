import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { lagGroupColumns, lagGroupDefaultVisible } from '../columns';
import { lagGroupFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['deviceId', 'Device Id'],
  ['lagName', 'Lag Name'],
  ['memberCount', 'Member Count'],
  ['lacpMode', 'Lacp Mode'],
  ['minLinks', 'Min Links'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
];

describe('lagGroup columns', () => {
  it('shows code, name and status by default', () => {
    expect(lagGroupDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(lagGroupColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = lagGroupColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(lagGroupFixture)}</MemoryRouter>);
  });
});
