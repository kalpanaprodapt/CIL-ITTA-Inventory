import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { dnsRecordColumns, dnsRecordDefaultVisible } from '../columns';
import { dnsRecordFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['zoneName', 'Zone Name'],
  ['recordType', 'Record Type'],
  ['recordValue', 'Record Value'],
  ['ttl', 'Ttl'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['owner', 'Owner'],
  ['criticality', 'Criticality'],
  ['costCenter', 'Cost Center'],
];

describe('dnsRecord columns', () => {
  it('shows code, name and status by default', () => {
    expect(dnsRecordDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(dnsRecordColumns).toHaveLength(14);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = dnsRecordColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(dnsRecordFixture)}</MemoryRouter>);
  });
});
