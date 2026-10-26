import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { changeRequestColumns, changeRequestDefaultVisible } from '../columns';
import { changeRequestFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['changeNumber', 'Change Number'],
  ['changeType', 'Change Type'],
  ['riskLevel', 'Risk Level'],
  ['plannedStart', 'Planned Start'],
  ['plannedEnd', 'Planned End'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
];

describe('changeRequest columns', () => {
  it('shows code, name and status by default', () => {
    expect(changeRequestDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(changeRequestColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = changeRequestColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(changeRequestFixture)}</MemoryRouter>);
  });
});
