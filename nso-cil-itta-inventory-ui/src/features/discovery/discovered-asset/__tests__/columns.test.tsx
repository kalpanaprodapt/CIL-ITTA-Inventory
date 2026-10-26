import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { discoveredAssetColumns, discoveredAssetDefaultVisible } from '../columns';
import { discoveredAssetFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['jobId', 'Job Id'],
  ['assetType', 'Asset Type'],
  ['fingerprint', 'Fingerprint'],
  ['rawPayload', 'Raw Payload'],
  ['matchedAssetId', 'Matched Asset Id'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
];

describe('discoveredAsset columns', () => {
  it('shows code, name and status by default', () => {
    expect(discoveredAssetDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(discoveredAssetColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = discoveredAssetColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(discoveredAssetFixture)}</MemoryRouter>);
  });
});
