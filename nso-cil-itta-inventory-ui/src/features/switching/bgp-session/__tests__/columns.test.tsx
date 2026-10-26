import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { bgpSessionColumns, bgpSessionDefaultVisible } from '../columns';
import { bgpSessionFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['localDeviceId', 'Local Device Id'],
  ['peerAddress', 'Peer Address'],
  ['localAsn', 'Local Asn'],
  ['remoteAsn', 'Remote Asn'],
  ['sessionState', 'Session State'],
  ['prefixLimit', 'Prefix Limit'],
  ['region', 'Region'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
  ['notes', 'Notes'],
];

describe('bgpSession columns', () => {
  it('shows code, name and status by default', () => {
    expect(bgpSessionDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(bgpSessionColumns).toHaveLength(16);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = bgpSessionColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(bgpSessionFixture)}</MemoryRouter>);
  });
});
