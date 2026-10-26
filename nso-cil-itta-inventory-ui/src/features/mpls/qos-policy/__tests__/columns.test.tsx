import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { qosPolicyColumns, qosPolicyDefaultVisible } from '../columns';
import { qosPolicyFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['policyName', 'Policy Name'],
  ['classCount', 'Class Count'],
  ['cirMbps', 'Cir Mbps'],
  ['pirMbps', 'Pir Mbps'],
  ['markingScheme', 'Marking Scheme'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['owner', 'Owner'],
];

describe('qosPolicy columns', () => {
  it('shows code, name and status by default', () => {
    expect(qosPolicyDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(qosPolicyColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = qosPolicyColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(qosPolicyFixture)}</MemoryRouter>);
  });
});
