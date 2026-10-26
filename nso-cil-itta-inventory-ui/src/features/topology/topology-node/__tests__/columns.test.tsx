import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { topologyNodeColumns, topologyNodeDefaultVisible } from '../columns';
import { topologyNodeFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['viewId', 'View Id'],
  ['entityRef', 'Entity Ref'],
  ['nodeType', 'Node Type'],
  ['xPos', 'X Pos'],
  ['yPos', 'Y Pos'],
  ['layer', 'Layer'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
];

describe('topologyNode columns', () => {
  it('shows code, name and status by default', () => {
    expect(topologyNodeDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(topologyNodeColumns).toHaveLength(16);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = topologyNodeColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(topologyNodeFixture)}</MemoryRouter>);
  });
});
