import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { reconciliationRuleColumns, reconciliationRuleDefaultVisible } from '../columns';
import { reconciliationRuleFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['ruleName', 'Rule Name'],
  ['matchKey', 'Match Key'],
  ['action', 'Action'],
  ['priority', 'Priority'],
  ['expression', 'Expression'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['owner', 'Owner'],
];

describe('reconciliationRule columns', () => {
  it('shows code, name and status by default', () => {
    expect(reconciliationRuleDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(reconciliationRuleColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = reconciliationRuleColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(reconciliationRuleFixture)}</MemoryRouter>);
  });
});
