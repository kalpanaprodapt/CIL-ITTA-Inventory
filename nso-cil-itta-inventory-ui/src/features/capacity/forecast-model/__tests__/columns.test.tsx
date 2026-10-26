import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { forecastModelColumns, forecastModelDefaultVisible } from '../columns';
import { forecastModelFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['modelName', 'Model Name'],
  ['algorithm', 'Algorithm'],
  ['trainedOn', 'Trained On'],
  ['mape', 'Mape'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
];

describe('forecastModel columns', () => {
  it('shows code, name and status by default', () => {
    expect(forecastModelDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(forecastModelColumns).toHaveLength(14);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = forecastModelColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(forecastModelFixture)}</MemoryRouter>);
  });
});
