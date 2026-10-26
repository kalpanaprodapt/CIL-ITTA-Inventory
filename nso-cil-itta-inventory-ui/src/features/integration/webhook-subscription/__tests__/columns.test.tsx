import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { webhookSubscriptionColumns, webhookSubscriptionDefaultVisible } from '../columns';
import { webhookSubscriptionFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['callbackUrl', 'Callback Url'],
  ['eventTypes', 'Event Types'],
  ['secretRef', 'Secret Ref'],
  ['active', 'Active'],
  ['retryLimit', 'Retry Limit'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['region', 'Region'],
  ['owner', 'Owner'],
];

describe('webhookSubscription columns', () => {
  it('shows code, name and status by default', () => {
    expect(webhookSubscriptionDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(webhookSubscriptionColumns).toHaveLength(15);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = webhookSubscriptionColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(webhookSubscriptionFixture)}</MemoryRouter>);
  });
});
