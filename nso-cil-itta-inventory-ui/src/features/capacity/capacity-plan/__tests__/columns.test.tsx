import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { capacityPlanColumns, capacityPlanDefaultVisible } from '../columns';
import { capacityPlanFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['planName', 'Plan Name'],
  ['horizonMonths', 'Horizon Months'],
  ['targetUtilizationPct', 'Target Utilization Pct'],
  ['owner', 'Owner'],
  ['approvedOn', 'Approved On'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
];

describe('capacityPlan columns', () => {
  it('shows code, name and status by default', () => {
    expect(capacityPlanDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(capacityPlanColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = capacityPlanColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(capacityPlanFixture)}</MemoryRouter>);
  });
});
