import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { eventSourceColumns, eventSourceDefaultVisible } from '../columns';
import { eventSourceFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['sourceName', 'Source Name'],
  ['protocol', 'Protocol'],
  ['endpoint', 'Endpoint'],
  ['heartbeatSeconds', 'Heartbeat Seconds'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['owner', 'Owner'],
];

describe('eventSource columns', () => {
  it('shows code, name and status by default', () => {
    expect(eventSourceDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(eventSourceColumns).toHaveLength(14);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = eventSourceColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(eventSourceFixture)}</MemoryRouter>);
  });
});
