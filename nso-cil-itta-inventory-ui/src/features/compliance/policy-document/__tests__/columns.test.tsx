import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { policyDocumentColumns, policyDocumentDefaultVisible } from '../columns';
import { policyDocumentFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['title', 'Title'],
  ['docVersion', 'Doc Version'],
  ['effectiveDate', 'Effective Date'],
  ['owner', 'Owner'],
  ['documentUrl', 'Document Url'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
];

describe('policyDocument columns', () => {
  it('shows code, name and status by default', () => {
    expect(policyDocumentDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(policyDocumentColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = policyDocumentColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(policyDocumentFixture)}</MemoryRouter>);
  });
});
