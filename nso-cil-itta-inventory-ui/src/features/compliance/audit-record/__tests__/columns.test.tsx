import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { auditRecordColumns, auditRecordDefaultVisible } from '../columns';
import { auditRecordFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['actor', 'Actor'],
  ['action', 'Action'],
  ['entityRef', 'Entity Ref'],
  ['occurredAt', 'Occurred At'],
  ['beforeState', 'Before State'],
  ['afterState', 'After State'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
];

describe('auditRecord columns', () => {
  it('shows code, name and status by default', () => {
    expect(auditRecordDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(auditRecordColumns).toHaveLength(16);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = auditRecordColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(auditRecordFixture)}</MemoryRouter>);
  });
});
