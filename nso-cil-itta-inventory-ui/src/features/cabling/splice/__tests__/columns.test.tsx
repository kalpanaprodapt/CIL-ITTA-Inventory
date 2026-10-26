import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { spliceColumns, spliceDefaultVisible } from '../columns';
import { spliceFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['enclosureId', 'Enclosure Id'],
  ['aStrandId', 'A Strand Id'],
  ['zStrandId', 'Z Strand Id'],
  ['lossDb', 'Loss Db'],
  ['splicedOn', 'Spliced On'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
];

describe('splice columns', () => {
  it('shows code, name and status by default', () => {
    expect(spliceDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(spliceColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = spliceColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(spliceFixture)}</MemoryRouter>);
  });
});
