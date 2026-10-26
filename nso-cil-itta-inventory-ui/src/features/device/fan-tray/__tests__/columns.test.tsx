import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { fanTrayColumns, fanTrayDefaultVisible } from '../columns';
import { fanTrayFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['chassisId', 'Chassis Id'],
  ['slotNumber', 'Slot Number'],
  ['fanCount', 'Fan Count'],
  ['airflow', 'Airflow'],
  ['serialNumber', 'Serial Number'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
];

describe('fanTray columns', () => {
  it('shows code, name and status by default', () => {
    expect(fanTrayDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(fanTrayColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = fanTrayColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(fanTrayFixture)}</MemoryRouter>);
  });
});
