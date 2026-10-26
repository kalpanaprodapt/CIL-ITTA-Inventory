import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { fieldTaskColumns, fieldTaskDefaultVisible } from '../columns';
import { fieldTaskFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['workOrderId', 'Work Order Id'],
  ['taskName', 'Task Name'],
  ['sequence', 'Sequence'],
  ['durationMinutes', 'Duration Minutes'],
  ['completedOn', 'Completed On'],
  ['region', 'Region'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
];

describe('fieldTask columns', () => {
  it('shows code, name and status by default', () => {
    expect(fieldTaskDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(fieldTaskColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = fieldTaskColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(fieldTaskFixture)}</MemoryRouter>);
  });
});
