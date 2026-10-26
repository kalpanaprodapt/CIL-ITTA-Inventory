import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { describe, expect, it } from 'vitest';
import { k8sClusterColumns, k8sClusterDefaultVisible } from '../columns';
import { k8sClusterFixture } from './fixtures';

const EXPECTED: [string, string][] = [
  ['code', 'Code'],
  ['name', 'Name'],
  ['description', 'Description'],
  ['status', 'Status'],
  ['externalRef', 'External Ref'],
  ['clusterName', 'Cluster Name'],
  ['clusterVersion', 'Cluster Version'],
  ['nodeCount', 'Node Count'],
  ['cloudAccountId', 'Cloud Account Id'],
  ['region', 'Region'],
  ['apiEndpoint', 'Api Endpoint'],
  ['effectiveTo', 'Effective To'],
  ['labels', 'Labels'],
  ['lifecycleStage', 'Lifecycle Stage'],
  ['sourceSystem', 'Source System'],
  ['owner', 'Owner'],
];

describe('k8sCluster columns', () => {
  it('shows code, name and status by default', () => {
    expect(k8sClusterDefaultVisible).toEqual(expect.arrayContaining(['code', 'name', 'status']));
  });

  it('has one column per field', () => {
    expect(k8sClusterColumns).toHaveLength(16);
  });

  it.each(EXPECTED)('renders the %s column', (key, header) => {
    const column = k8sClusterColumns.find((c) => c.key === key);
    expect(column).toBeDefined();
    expect(column?.header).toBe(header);
    render(<MemoryRouter>{column?.render(k8sClusterFixture)}</MemoryRouter>);
  });
});
