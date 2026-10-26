import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { cloudAccountColumns, cloudAccountDefaultVisible } from '../columns';
import { cloudAccountFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['provider', 'Provider'],
  ['accountRef', 'Account Ref'],
  ['subscriptionName', 'Subscription Name'],
  ['monthlyBudget', 'Monthly Budget'],
  ['environment', 'Environment'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
];

describe('cloudAccount columns', () => {
  it('shows code, name and status by default', () => {
    expect(cloudAccountDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(cloudAccountColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = cloudAccountColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(cloudAccountFixture)}</MemoryRouter>);
  });
});
