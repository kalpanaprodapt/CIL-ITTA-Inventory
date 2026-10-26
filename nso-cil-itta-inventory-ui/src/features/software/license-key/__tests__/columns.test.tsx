import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { licenseKeyColumns, licenseKeyDefaultVisible } from '../columns';
import { licenseKeyFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['poolId', 'Pool Id'],
  ['keyValue', 'Key Value'],
  ['feature', 'Feature'],
  ['expiresOn', 'Expires On'],
  ['assignedDeviceId', 'Assigned Device Id'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
];

describe('licenseKey columns', () => {
  it('shows code, name and status by default', () => {
    expect(licenseKeyDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(licenseKeyColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = licenseKeyColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(licenseKeyFixture)}</MemoryRouter>);
  });
});
