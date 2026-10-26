import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest';
import {
  bulkCreateServiceOrders,
  changeServiceOrderStatus,
  createServiceOrder,
  deleteServiceOrder,
  getServiceOrder,
  getServiceOrderByCode,
  searchServiceOrders,
  updateServiceOrder,
} from '../api';
import { serviceOrderFixture, serviceOrderInput, serviceOrderPage } from './fixtures';

const BASE = '/api/v1/service-orders';

function respond(body: unknown, status = 200) {
  return Promise.resolve(
    new Response(status === 204 ? null : JSON.stringify(body), {
      status,
      headers: { 'Content-Type': 'application/json' },
    }),
  );
}

describe('serviceOrder api', () => {
  const fetchMock = vi.fn();

  beforeEach(() => {
    fetchMock.mockReset();
    vi.stubGlobal('fetch', fetchMock);
  });

  afterEach(() => {
    vi.unstubAllGlobals();
  });

  it('searches with filter, paging and sort', async () => {
    fetchMock.mockReturnValue(respond(serviceOrderPage));
    const page = await searchServiceOrders({ q: 'core', status: ['ACTIVE'] }, 2, 50, 'name,desc');
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
    fetchMock.mockReturnValue(respond(serviceOrderFixture));
    const item = await getServiceOrder(serviceOrderFixture.id);
    expect(String(fetchMock.mock.calls[0][0])).toBe(`${BASE}/${serviceOrderFixture.id}`);
    expect(item.code).toBe(serviceOrderFixture.code);
  });

  it('gets by code with encoding', async () => {
    fetchMock.mockReturnValue(respond(serviceOrderFixture));
    await getServiceOrderByCode('A/B 1');
    expect(String(fetchMock.mock.calls[0][0])).toBe(`${BASE}/by-code/A%2FB%201`);
  });

  it('posts create payloads as JSON', async () => {
    fetchMock.mockReturnValue(respond(serviceOrderFixture, 201));
    await createServiceOrder(serviceOrderInput);
    const init = fetchMock.mock.calls[0][1] as RequestInit;
    expect(init.method).toBe('POST');
    expect(JSON.parse(String(init.body)).code).toBe(serviceOrderInput.code);
  });

  it('puts updates with expected version', async () => {
    fetchMock.mockReturnValue(respond(serviceOrderFixture));
    await updateServiceOrder(serviceOrderFixture.id, { name: 'Renamed', expectedVersion: 3 });
    const init = fetchMock.mock.calls[0][1] as RequestInit;
    expect(init.method).toBe('PUT');
    expect(JSON.parse(String(init.body)).expectedVersion).toBe(3);
  });

  it('patches status through the query string', async () => {
    fetchMock.mockReturnValue(respond(serviceOrderFixture));
    await changeServiceOrderStatus(serviceOrderFixture.id, 'MAINTENANCE');
    expect(String(fetchMock.mock.calls[0][0])).toContain('status=MAINTENANCE');
  });

  it('deletes and tolerates 204', async () => {
    fetchMock.mockReturnValue(respond(null, 204));
    await expect(deleteServiceOrder(serviceOrderFixture.id)).resolves.toBeUndefined();
  });

  it('bulk creates', async () => {
    fetchMock.mockReturnValue(respond({ succeeded: [serviceOrderFixture], failures: [] }));
    const result = await bulkCreateServiceOrders([serviceOrderInput]);
    expect(result.succeeded).toHaveLength(1);
  });

  it('surfaces api errors', async () => {
    fetchMock.mockReturnValue(respond({ message: 'Not found' }, 404));
    await expect(getServiceOrder('missing')).rejects.toMatchObject({ status: 404 });
  });
});
