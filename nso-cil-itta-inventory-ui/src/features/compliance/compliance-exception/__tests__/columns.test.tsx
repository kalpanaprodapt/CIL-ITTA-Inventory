import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { complianceExceptionColumns, complianceExceptionDefaultVisible } from '../columns';
import { complianceExceptionFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['ruleId', 'Rule Id'],
  ['targetRef', 'Target Ref'],
  ['justification', 'Justification'],
  ['approvedBy', 'Approved By'],
  ['expiresOn', 'Expires On'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
];

describe('complianceException columns', () => {
  it('shows code, name and status by default', () => {
    expect(complianceExceptionDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(complianceExceptionColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = complianceExceptionColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(complianceExceptionFixture)}</MemoryRouter>);
  });
});
