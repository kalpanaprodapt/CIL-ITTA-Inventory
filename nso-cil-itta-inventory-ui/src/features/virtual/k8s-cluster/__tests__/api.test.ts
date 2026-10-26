import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest';
import {
  bulkCreateK8sClusters,
  changeK8sClusterStatus,
  createK8sCluster,
  deleteK8sCluster,
  getK8sCluster,
  getK8sClusterByCode,
  searchK8sClusters,
  updateK8sCluster,
} from '../api';
import { k8sClusterFixture, k8sClusterInput, k8sClusterPage } from './fixtures';

const BASE = '/api/v1/k8s-clusters';

function respond(body: unknown, status = 200) {
  return Promise.resolve(
    new Response(status === 204 ? null : JSON.stringify(body), {
      status,
      headers: { 'Content-Type': 'application/json' },
    }),
  );
}

describe('k8sCluster api', () => {
  const fetchMock = vi.fn();

  beforeEach(() => {
    fetchMock.mockReset();
    vi.stubGlobal('fetch', fetchMock);
  });

  afterEach(() => {
    vi.unstubAllGlobals();
  });

  it('searches with filter, paging and sort', async () => {
    fetchMock.mockReturnValue(respond(k8sClusterPage));
    const page = await searchK8sClusters({ q: 'core', status: ['ACTIVE'] }, 2, 50, 'name,desc');
    const url = new URL(String(fetchMock.mock.calls[0][0]), 'http://localhost');
    expect(url.pathname).toBe(BASE);
    expect(url.searchParams.get('q')).toBe('core');
    expect(url.searchParams.get('status')).toBe('ACTIVE');
    expect(url.searchParams.get('page')).toBe('2');
    expect(url.searchParams.get('size')).toBe('50');
    expect(url.searchParams.get('sort')).toBe('name,desc');
    expect(page.totalElements).toBe(1);
  });

  it('gets by id', async () => {
    fetchMock.mockReturnValue(respond(k8sClusterFixture));
    const item = await getK8sCluster(k8sClusterFixture.id);
    expect(String(fetchMock.mock.calls[0][0])).toBe(`${BASE}/${k8sClusterFixture.id}`);
    expect(item.code).toBe(k8sClusterFixture.code);
  });

  it('gets by code with encoding', async () => {
    fetchMock.mockReturnValue(respond(k8sClusterFixture));
    await getK8sClusterByCode('A/B 1');
    expect(String(fetchMock.mock.calls[0][0])).toBe(`${BASE}/by-code/A%2FB%201`);
  });

  it('posts create payloads as JSON', async () => {
    fetchMock.mockReturnValue(respond(k8sClusterFixture, 201));
    await createK8sCluster(k8sClusterInput);
    const init = fetchMock.mock.calls[0][1] as RequestInit;
    expect(init.method).toBe('POST');
    expect(JSON.parse(String(init.body)).code).toBe(k8sClusterInput.code);
  });

  it('puts updates with expected version', async () => {
    fetchMock.mockReturnValue(respond(k8sClusterFixture));
    await updateK8sCluster(k8sClusterFixture.id, { name: 'Renamed', expectedVersion: 3 });
    const init = fetchMock.mock.calls[0][1] as RequestInit;
    expect(init.method).toBe('PUT');
    expect(JSON.parse(String(init.body)).expectedVersion).toBe(3);
  });

  it('patches status through the query string', async () => {
    fetchMock.mockReturnValue(respond(k8sClusterFixture));
    await changeK8sClusterStatus(k8sClusterFixture.id, 'MAINTENANCE');
    expect(String(fetchMock.mock.calls[0][0])).toContain('status=MAINTENANCE');
  });

  it('deletes and tolerates 204', async () => {
    fetchMock.mockReturnValue(respond(null, 204));
    await expect(deleteK8sCluster(k8sClusterFixture.id)).resolves.toBeUndefined();
  });

  it('bulk creates', async () => {
    fetchMock.mockReturnValue(respond({ succeeded: [k8sClusterFixture], failures: [] }));
    const result = await bulkCreateK8sClusters([k8sClusterInput]);
    expect(result.succeeded).toHaveLength(1);
  });

  it('surfaces api errors', async () => {
    fetchMock.mockReturnValue(respond({ message: 'Not found' }, 404));
    await expect(getK8sCluster('missing')).rejects.toMatchObject({ status: 404 });
  });
});
