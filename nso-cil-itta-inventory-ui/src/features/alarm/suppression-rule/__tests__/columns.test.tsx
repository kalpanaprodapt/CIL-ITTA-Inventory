import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { suppressionRuleColumns, suppressionRuleDefaultVisible } from '../columns';
import { suppressionRuleFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['ruleName', 'Rule Name'],
  ['matchExpression', 'Match Expression'],
  ['startsAt', 'Starts At'],
  ['endsAt', 'Ends At'],
  ['reason', 'Reason'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
];

describe('suppressionRule columns', () => {
  it('shows code, name and status by default', () => {
    expect(suppressionRuleDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(suppressionRuleColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = suppressionRuleColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(suppressionRuleFixture)}</MemoryRouter>);
  });
});
