import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { assetColumns, assetDefaultVisible } from '../columns';
import { assetFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['assetTag', 'Asset Tag'],
  ['assetClass', 'Asset Class'],
  ['acquiredOn', 'Acquired On'],
  ['purchasePrice', 'Purchase Price'],
  ['currentValue', 'Current Value'],
  ['custodian', 'Custodian'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['owner', 'Owner'],
];

describe('asset columns', () => {
  it('shows code, name and status by default', () => {
    expect(assetDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(assetColumns).toHaveLength(16);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = assetColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(assetFixture)}</MemoryRouter>);
  });
});
