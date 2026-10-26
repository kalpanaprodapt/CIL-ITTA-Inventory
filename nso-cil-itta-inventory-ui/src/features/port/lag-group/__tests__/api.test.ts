import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest';
import {
  bulkCreateLagGroups,
  changeLagGroupStatus,
  createLagGroup,
  deleteLagGroup,
  getLagGroup,
  getLagGroupByCode,
  searchLagGroups,
  updateLagGroup,
} from '../api';
import { lagGroupFixture, lagGroupInput, lagGroupPage } from './fixtures';

const BASE = '/api/v1/lag-groups';

function respond(body: unknown, status = 200) {
  return Promise.resolve(
    new Response(status === 204 ? null : JSON.stringify(body), {
      status,
      headers: { 'Content-Type': 'application/json' },
    }),
  );
}

describe('lagGroup api', () => {
  const fetchMock = vi.fn();

  beforeEach(() => {
    fetchMock.mockReset();
    vi.stubGlobal('fetch', fetchMock);
  });

  afterEach(() => {
    vi.unstubAllGlobals();
  });

  it('searches with filter, paging and sort', async () => {
    fetchMock.mockReturnValue(respond(lagGroupPage));
    const page = await searchLagGroups({ q: 'core', status: ['ACTIVE'] }, 2, 50, 'name,desc');
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
    fetchMock.mockReturnValue(respond(lagGroupFixture));
    const item = await getLagGroup(lagGroupFixture.id);
    expect(String(fetchMock.mock.calls[0][0])).toBe(`${BASE}/${lagGroupFixture.id}`);
    expect(item.code).toBe(lagGroupFixture.code);
  });

  it('gets by code with encoding', async () => {
    fetchMock.mockReturnValue(respond(lagGroupFixture));
    await getLagGroupByCode('A/B 1');
    expect(String(fetchMock.mock.calls[0][0])).toBe(`${BASE}/by-code/A%2FB%201`);
  });

  it('posts create payloads as JSON', async () => {
    fetchMock.mockReturnValue(respond(lagGroupFixture, 201));
    await createLagGroup(lagGroupInput);
    const init = fetchMock.mock.calls[0][1] as RequestInit;
    expect(init.method).toBe('POST');
    expect(JSON.parse(String(init.body)).code).toBe(lagGroupInput.code);
  });

  it('puts updates with expected version', async () => {
    fetchMock.mockReturnValue(respond(lagGroupFixture));
    await updateLagGroup(lagGroupFixture.id, { name: 'Renamed', expectedVersion: 3 });
    const init = fetchMock.mock.calls[0][1] as RequestInit;
    expect(init.method).toBe('PUT');
    expect(JSON.parse(String(init.body)).expectedVersion).toBe(3);
  });

  it('patches status through the query string', async () => {
    fetchMock.mockReturnValue(respond(lagGroupFixture));
    await changeLagGroupStatus(lagGroupFixture.id, 'MAINTENANCE');
    expect(String(fetchMock.mock.calls[0][0])).toContain('status=MAINTENANCE');
  });

  it('deletes and tolerates 204', async () => {
    fetchMock.mockReturnValue(respond(null, 204));
    await expect(deleteLagGroup(lagGroupFixture.id)).resolves.toBeUndefined();
  });

  it('bulk creates', async () => {
    fetchMock.mockReturnValue(respond({ succeeded: [lagGroupFixture], failures: [] }));
    const result = await bulkCreateLagGroups([lagGroupInput]);
    expect(result.succeeded).toHaveLength(1);
  });

  it('surfaces api errors', async () => {
    fetchMock.mockReturnValue(respond({ message: 'Not found' }, 404));
    await expect(getLagGroup('missing')).rejects.toMatchObject({ status: 404 });
  });
});
