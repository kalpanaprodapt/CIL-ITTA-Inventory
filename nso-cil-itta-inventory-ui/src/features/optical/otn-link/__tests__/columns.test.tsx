import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { otnLinkColumns, otnLinkDefaultVisible } from '../columns';
import { otnLinkFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['aNodeId', 'A Node Id'],
  ['zNodeId', 'Z Node Id'],
  ['odu', 'Odu'],
  ['latencyUs', 'Latency Us'],
  ['protection', 'Protection'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
];

describe('otnLink columns', () => {
  it('shows code, name and status by default', () => {
    expect(otnLinkDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(otnLinkColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = otnLinkColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(otnLinkFixture)}</MemoryRouter>);
  });
});
