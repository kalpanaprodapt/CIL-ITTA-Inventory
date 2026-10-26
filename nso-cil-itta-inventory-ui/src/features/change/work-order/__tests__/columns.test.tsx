import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { workOrderColumns, workOrderDefaultVisible } from '../columns';
import { workOrderFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['workOrderNumber', 'Work Order Number'],
  ['workType', 'Work Type'],
  ['assignedTechnicianId', 'Assigned Technician Id'],
  ['dueDate', 'Due Date'],
  ['workState', 'Work State'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
];

describe('workOrder columns', () => {
  it('shows code, name and status by default', () => {
    expect(workOrderDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(workOrderColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = workOrderColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(workOrderFixture)}</MemoryRouter>);
  });
});
