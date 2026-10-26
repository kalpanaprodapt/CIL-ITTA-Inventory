import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { alarmInstanceColumns, alarmInstanceDefaultVisible } from '../columns';
import { alarmInstanceFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['definitionId', 'Definition Id'],
  ['sourceRef', 'Source Ref'],
  ['raisedAt', 'Raised At'],
  ['clearedAt', 'Cleared At'],
  ['ackedBy', 'Acked By'],
  ['alarmState', 'Alarm State'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
];

describe('alarmInstance columns', () => {
  it('shows code, name and status by default', () => {
    expect(alarmInstanceDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(alarmInstanceColumns).toHaveLength(16);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = alarmInstanceColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(alarmInstanceFixture)}</MemoryRouter>);
  });
});
