import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { depreciationScheduleColumns, depreciationScheduleDefaultVisible } from '../columns';
import { depreciationScheduleFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['assetId', 'Asset Id'],
  ['method', 'Method'],
  ['usefulLifeMonths', 'Useful Life Months'],
  ['salvageValue', 'Salvage Value'],
  ['startDate', 'Start Date'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
];

describe('depreciationSchedule columns', () => {
  it('shows code, name and status by default', () => {
    expect(depreciationScheduleDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(depreciationScheduleColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = depreciationScheduleColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(depreciationScheduleFixture)}</MemoryRouter>);
  });
});
