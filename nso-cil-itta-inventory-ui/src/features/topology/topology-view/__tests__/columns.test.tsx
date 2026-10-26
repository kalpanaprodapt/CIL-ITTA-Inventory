import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { topologyViewColumns, topologyViewDefaultVisible } from '../columns';
import { topologyViewFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['viewName', 'View Name'],
  ['viewType', 'View Type'],
  ['scopeFilter', 'Scope Filter'],
  ['refreshedAt', 'Refreshed At'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
];

describe('topologyView columns', () => {
  it('shows code, name and status by default', () => {
    expect(topologyViewDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(topologyViewColumns).toHaveLength(14);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = topologyViewColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(topologyViewFixture)}</MemoryRouter>);
  });
});
