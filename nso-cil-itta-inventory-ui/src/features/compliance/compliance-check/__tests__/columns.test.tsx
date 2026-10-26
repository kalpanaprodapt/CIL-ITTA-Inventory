import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { complianceCheckColumns, complianceCheckDefaultVisible } from '../columns';
import { complianceCheckFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['ruleId', 'Rule Id'],
  ['targetRef', 'Target Ref'],
  ['checkedAt', 'Checked At'],
  ['result', 'Result'],
  ['evidence', 'Evidence'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
];

describe('complianceCheck columns', () => {
  it('shows code, name and status by default', () => {
    expect(complianceCheckDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(complianceCheckColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = complianceCheckColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(complianceCheckFixture)}</MemoryRouter>);
  });
});
