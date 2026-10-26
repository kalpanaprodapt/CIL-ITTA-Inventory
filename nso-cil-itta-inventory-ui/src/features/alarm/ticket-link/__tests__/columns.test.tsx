import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { ticketLinkColumns, ticketLinkDefaultVisible } from '../columns';
import { ticketLinkFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['alarmId', 'Alarm Id'],
  ['ticketSystem', 'Ticket System'],
  ['ticketNumber', 'Ticket Number'],
  ['linkedAt', 'Linked At'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
];

describe('ticketLink columns', () => {
  it('shows code, name and status by default', () => {
    expect(ticketLinkDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(ticketLinkColumns).toHaveLength(14);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = ticketLinkColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(ticketLinkFixture)}</MemoryRouter>);
  });
});
