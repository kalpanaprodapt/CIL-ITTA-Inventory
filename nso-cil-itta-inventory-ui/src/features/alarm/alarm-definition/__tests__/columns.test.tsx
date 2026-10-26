import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { alarmDefinitionColumns, alarmDefinitionDefaultVisible } from '../columns';
import { alarmDefinitionFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['alarmCode', 'Alarm Code'],
  ['severity', 'Severity'],
  ['probableCause', 'Probable Cause'],
  ['autoClear', 'Auto Clear'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
];

describe('alarmDefinition columns', () => {
  it('shows code, name and status by default', () => {
    expect(alarmDefinitionDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(alarmDefinitionColumns).toHaveLength(14);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = alarmDefinitionColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(alarmDefinitionFixture)}</MemoryRouter>);
  });
});
