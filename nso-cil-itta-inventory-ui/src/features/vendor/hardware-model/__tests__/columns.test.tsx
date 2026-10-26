import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { hardwareModelColumns, hardwareModelDefaultVisible } from '../columns';
import { hardwareModelFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['vendorId', 'Vendor Id'],
  ['modelNumber', 'Model Number'],
  ['formFactor', 'Form Factor'],
  ['powerWatts', 'Power Watts'],
  ['eolDate', 'Eol Date'],
  ['eosDate', 'Eos Date'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['owner', 'Owner'],
];

describe('hardwareModel columns', () => {
  it('shows code, name and status by default', () => {
    expect(hardwareModelDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(hardwareModelColumns).toHaveLength(16);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = hardwareModelColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(hardwareModelFixture)}</MemoryRouter>);
  });
});
