import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { reconciliationResultColumns, reconciliationResultDefaultVisible } from '../columns';
import { reconciliationResultFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['jobId', 'Job Id'],
  ['ruleId', 'Rule Id'],
  ['outcome', 'Outcome'],
  ['diffSummary', 'Diff Summary'],
  ['resolvedBy', 'Resolved By'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
];

describe('reconciliationResult columns', () => {
  it('shows code, name and status by default', () => {
    expect(reconciliationResultDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(reconciliationResultColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = reconciliationResultColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(reconciliationResultFixture)}</MemoryRouter>);
  });
});
