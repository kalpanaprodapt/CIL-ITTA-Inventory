import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { pseudoWireColumns, pseudoWireDefaultVisible } from '../columns';
import { pseudoWireFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['vcId', 'Vc Id'],
  ['aEndId', 'A End Id'],
  ['zEndId', 'Z End Id'],
  ['encapsulation', 'Encapsulation'],
  ['controlWord', 'Control Word'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
];

describe('pseudoWire columns', () => {
  it('shows code, name and status by default', () => {
    expect(pseudoWireDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(pseudoWireColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = pseudoWireColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(pseudoWireFixture)}</MemoryRouter>);
  });
});
