import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest';
import {
  bulkCreateIpPools,
  changeIpPoolStatus,
  createIpPool,
  deleteIpPool,
  getIpPool,
  getIpPoolByCode,
  searchIpPools,
  updateIpPool,
} from '../api';
import { ipPoolFixture, ipPoolInput, ipPoolPage } from './fixtures';

const BASE = '/api/v1/ip-pools';

function respond(body: unknown, status = 200) {
  return Promise.resolve(
    new Response(status === 204 ? null : JSON.stringify(body), {
      status,
      headers: { 'Content-Type': 'application/json' },
    }),
  );
}

describe('ipPool api', () => {
  const fetchMock = vi.fn();

  beforeEach(() => {
    fetchMock.mockReset();
    vi.stubGlobal('fetch', fetchMock);
  });

  afterEach(() => {
    vi.unstubAllGlobals();
  });

  it('searches with filter, paging and sort', async () => {
    fetchMock.mockReturnValue(respond(ipPoolPage));
    const page = await searchIpPools({ q: 'core', status: ['ACTIVE'] }, 2, 50, 'name,desc');
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
    fetchMock.mockReturnValue(respond(ipPoolFixture));
    const item = await getIpPool(ipPoolFixture.id);
    expect(String(fetchMock.mock.calls[0][0])).toBe(`${BASE}/${ipPoolFixture.id}`);
    expect(item.code).toBe(ipPoolFixture.code);
  });

  it('gets by code with encoding', async () => {
    fetchMock.mockReturnValue(respond(ipPoolFixture));
    await getIpPoolByCode('A/B 1');
    expect(String(fetchMock.mock.calls[0][0])).toBe(`${BASE}/by-code/A%2FB%201`);
  });

  it('posts create payloads as JSON', async () => {
    fetchMock.mockReturnValue(respond(ipPoolFixture, 201));
    await createIpPool(ipPoolInput);
    const init = fetchMock.mock.calls[0][1] as RequestInit;
    expect(init.method).toBe('POST');
    expect(JSON.parse(String(init.body)).code).toBe(ipPoolInput.code);
  });

  it('puts updates with expected version', async () => {
    fetchMock.mockReturnValue(respond(ipPoolFixture));
    await updateIpPool(ipPoolFixture.id, { name: 'Renamed', expectedVersion: 3 });
    const init = fetchMock.mock.calls[0][1] as RequestInit;
    expect(init.method).toBe('PUT');
    expect(JSON.parse(String(init.body)).expectedVersion).toBe(3);
  });

  it('patches status through the query string', async () => {
    fetchMock.mockReturnValue(respond(ipPoolFixture));
    await changeIpPoolStatus(ipPoolFixture.id, 'MAINTENANCE');
    expect(String(fetchMock.mock.calls[0][0])).toContain('status=MAINTENANCE');
  });

  it('deletes and tolerates 204', async () => {
    fetchMock.mockReturnValue(respond(null, 204));
    await expect(deleteIpPool(ipPoolFixture.id)).resolves.toBeUndefined();
  });

  it('bulk creates', async () => {
    fetchMock.mockReturnValue(respond({ succeeded: [ipPoolFixture], failures: [] }));
    const result = await bulkCreateIpPools([ipPoolInput]);
    expect(result.succeeded).toHaveLength(1);
  });

  it('surfaces api errors', async () => {
    fetchMock.mockReturnValue(respond({ message: 'Not found' }, 404));
    await expect(getIpPool('missing')).rejects.toMatchObject({ status: 404 });
  });
});
