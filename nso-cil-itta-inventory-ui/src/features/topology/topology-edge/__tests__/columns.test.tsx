import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { topologyEdgeColumns, topologyEdgeDefaultVisible } from '../columns';
import { topologyEdgeFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['viewId', 'View Id'],
  ['sourceNodeId', 'Source Node Id'],
  ['targetNodeId', 'Target Node Id'],
  ['weight', 'Weight'],
  ['directed', 'Directed'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
];

describe('topologyEdge columns', () => {
  it('shows code, name and status by default', () => {
    expect(topologyEdgeDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(topologyEdgeColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = topologyEdgeColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(topologyEdgeFixture)}</MemoryRouter>);
  });
});
