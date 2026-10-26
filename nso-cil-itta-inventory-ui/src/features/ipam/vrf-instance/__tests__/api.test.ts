import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest';
import {
  bulkCreateVrfInstances,
  changeVrfInstanceStatus,
  createVrfInstance,
  deleteVrfInstance,
  getVrfInstance,
  getVrfInstanceByCode,
  searchVrfInstances,
  updateVrfInstance,
} from '../api';
import { vrfInstanceFixture, vrfInstanceInput, vrfInstancePage } from './fixtures';

const BASE = '/api/v1/vrf-instances';

function respond(body: unknown, status = 200) {
  return Promise.resolve(
    new Response(status === 204 ? null : JSON.stringify(body), {
      status,
      headers: { 'Content-Type': 'application/json' },
    }),
  );
}

describe('vrfInstance api', () => {
  const fetchMock = vi.fn();

  beforeEach(() => {
    fetchMock.mockReset();
    vi.stubGlobal('fetch', fetchMock);
  });

  afterEach(() => {
    vi.unstubAllGlobals();
  });

  it('searches with filter, paging and sort', async () => {
    fetchMock.mockReturnValue(respond(vrfInstancePage));
    const page = await searchVrfInstances({ q: 'core', status: ['ACTIVE'] }, 2, 50, 'name,desc');
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
    fetchMock.mockReturnValue(respond(vrfInstanceFixture));
    const item = await getVrfInstance(vrfInstanceFixture.id);
    expect(String(fetchMock.mock.calls[0][0])).toBe(`${BASE}/${vrfInstanceFixture.id}`);
    expect(item.code).toBe(vrfInstanceFixture.code);
  });

  it('gets by code with encoding', async () => {
    fetchMock.mockReturnValue(respond(vrfInstanceFixture));
    await getVrfInstanceByCode('A/B 1');
    expect(String(fetchMock.mock.calls[0][0])).toBe(`${BASE}/by-code/A%2FB%201`);
  });

  it('posts create payloads as JSON', async () => {
    fetchMock.mockReturnValue(respond(vrfInstanceFixture, 201));
    await createVrfInstance(vrfInstanceInput);
    const init = fetchMock.mock.calls[0][1] as RequestInit;
    expect(init.method).toBe('POST');
    expect(JSON.parse(String(init.body)).code).toBe(vrfInstanceInput.code);
  });

  it('puts updates with expected version', async () => {
    fetchMock.mockReturnValue(respond(vrfInstanceFixture));
    await updateVrfInstance(vrfInstanceFixture.id, { name: 'Renamed', expectedVersion: 3 });
    const init = fetchMock.mock.calls[0][1] as RequestInit;
    expect(init.method).toBe('PUT');
    expect(JSON.parse(String(init.body)).expectedVersion).toBe(3);
  });

  it('patches status through the query string', async () => {
    fetchMock.mockReturnValue(respond(vrfInstanceFixture));
    await changeVrfInstanceStatus(vrfInstanceFixture.id, 'MAINTENANCE');
    expect(String(fetchMock.mock.calls[0][0])).toContain('status=MAINTENANCE');
  });

  it('deletes and tolerates 204', async () => {
    fetchMock.mockReturnValue(respond(null, 204));
    await expect(deleteVrfInstance(vrfInstanceFixture.id)).resolves.toBeUndefined();
  });

  it('bulk creates', async () => {
    fetchMock.mockReturnValue(respond({ succeeded: [vrfInstanceFixture], failures: [] }));
    const result = await bulkCreateVrfInstances([vrfInstanceInput]);
    expect(result.succeeded).toHaveLength(1);
  });

  it('surfaces api errors', async () => {
    fetchMock.mockReturnValue(respond({ message: 'Not found' }, 404));
    await expect(getVrfInstance('missing')).rejects.toMatchObject({ status: 404 });
  });
});
