import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { transceiverColumns, transceiverDefaultVisible } from '../columns';
import { transceiverFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['portId', 'Port Id'],
  ['formFactor', 'Form Factor'],
  ['wavelengthNm', 'Wavelength Nm'],
  ['reachKm', 'Reach Km'],
  ['serialNumber', 'Serial Number'],
  ['vendorPart', 'Vendor Part'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['owner', 'Owner'],
];

describe('transceiver columns', () => {
  it('shows code, name and status by default', () => {
    expect(transceiverDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(transceiverColumns).toHaveLength(16);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = transceiverColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(transceiverFixture)}</MemoryRouter>);
  });
});
