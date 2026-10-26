import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { importBatchColumns, importBatchDefaultVisible } from '../columns';
import { importBatchFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['fileName', 'File Name'],
  ['entityType', 'Entity Type'],
  ['totalRows', 'Total Rows'],
  ['processedRows', 'Processed Rows'],
  ['failedRows', 'Failed Rows'],
  ['batchState', 'Batch State'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
];

describe('importBatch columns', () => {
  it('shows code, name and status by default', () => {
    expect(importBatchDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(importBatchColumns).toHaveLength(16);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = importBatchColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(importBatchFixture)}</MemoryRouter>);
  });
});
