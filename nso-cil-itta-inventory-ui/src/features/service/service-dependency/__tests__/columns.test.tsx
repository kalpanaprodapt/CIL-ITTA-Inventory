import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { serviceDependencyColumns, serviceDependencyDefaultVisible } from '../columns';
import { serviceDependencyFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['serviceId', 'Service Id'],
  ['dependsOnId', 'Depends On Id'],
  ['dependencyType', 'Dependency Type'],
  ['impactScore', 'Impact Score'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
];

describe('serviceDependency columns', () => {
  it('shows code, name and status by default', () => {
    expect(serviceDependencyDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(serviceDependencyColumns).toHaveLength(14);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = serviceDependencyColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(serviceDependencyFixture)}</MemoryRouter>);
  });
});
