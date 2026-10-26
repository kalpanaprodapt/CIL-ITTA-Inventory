import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { opticalChannelColumns, opticalChannelDefaultVisible } from '../columns';
import { opticalChannelFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['wavelengthNm', 'Wavelength Nm'],
  ['channelNumber', 'Channel Number'],
  ['gridType', 'Grid Type'],
  ['bitrateGbps', 'Bitrate Gbps'],
  ['systemId', 'System Id'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
  ['effectiveFrom', 'Effective From'],
  ['effectiveTo', 'Effective To'],
];

describe('opticalChannel columns', () => {
  it('shows code, name and status by default', () => {
    expect(opticalChannelDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(opticalChannelColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = opticalChannelColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(opticalChannelFixture)}</MemoryRouter>);
  });
});
