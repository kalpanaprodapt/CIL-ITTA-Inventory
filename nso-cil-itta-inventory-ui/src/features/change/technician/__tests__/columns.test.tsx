import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { technicianColumns, technicianDefaultVisible } from '../columns';
import { technicianFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['employeeId', 'Employee Id'],
  ['fullName', 'Full Name'],
  ['skillSet', 'Skill Set'],
  ['homeSiteId', 'Home Site Id'],
  ['certificationLevel', 'Certification Level'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
];

describe('technician columns', () => {
  it('shows code, name and status by default', () => {
    expect(technicianDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(technicianColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = technicianColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(technicianFixture)}</MemoryRouter>);
  });
});
