import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { circuitOrderColumns, circuitOrderDefaultVisible } from '../columns';
import { circuitOrderFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['circuitId', 'Circuit Id'],
  ['orderNumber', 'Order Number'],
  ['orderedOn', 'Ordered On'],
  ['requestedDate', 'Requested Date'],
  ['committedDate', 'Committed Date'],
  ['orderStatus', 'Order Status'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
];

describe('circuitOrder columns', () => {
  it('shows code, name and status by default', () => {
    expect(circuitOrderDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(circuitOrderColumns).toHaveLength(16);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = circuitOrderColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(circuitOrderFixture)}</MemoryRouter>);
  });
});
