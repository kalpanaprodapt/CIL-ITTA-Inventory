import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { circuitTerminationColumns, circuitTerminationDefaultVisible } from '../columns';
import { circuitTerminationFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['circuitId', 'Circuit Id'],
  ['side', 'Side'],
  ['portId', 'Port Id'],
  ['xconnectId', 'Xconnect Id'],
  ['patchInfo', 'Patch Info'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
];

describe('circuitTermination columns', () => {
  it('shows code, name and status by default', () => {
    expect(circuitTerminationDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(circuitTerminationColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = circuitTerminationColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(circuitTerminationFixture)}</MemoryRouter>);
  });
});
