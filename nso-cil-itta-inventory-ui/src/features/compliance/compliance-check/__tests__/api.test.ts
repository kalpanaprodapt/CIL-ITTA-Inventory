import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest';
import {
  bulkCreateComplianceChecks,
  changeComplianceCheckStatus,
  createComplianceCheck,
  deleteComplianceCheck,
  getComplianceCheck,
  getComplianceCheckByCode,
  searchComplianceChecks,
  updateComplianceCheck,
} from '../api';
import { complianceCheckFixture, complianceCheckInput, complianceCheckPage } from './fixtures';

const BASE = '/api/v1/compliance-checks';

function respond(body: unknown, status = 200) {
  return Promise.resolve(
    new Response(status === 204 ? null : JSON.stringify(body), {
      status,
      headers: { 'Content-Type': 'application/json' },
    }),
  );
}

describe('complianceCheck api', () => {
  const fetchMock = vi.fn();

  beforeEach(() => {
    fetchMock.mockReset();
    vi.stubGlobal('fetch', fetchMock);
  });

  afterEach(() => {
    vi.unstubAllGlobals();
  });

  it('searches with filter, paging and sort', async () => {
    fetchMock.mockReturnValue(respond(complianceCheckPage));
    const page = await searchComplianceChecks({ q: 'core', status: ['ACTIVE'] }, 2, 50, 'name,desc');
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
    fetchMock.mockReturnValue(respond(complianceCheckFixture));
    const item = await getComplianceCheck(complianceCheckFixture.id);
    expect(String(fetchMock.mock.calls[0][0])).toBe(`${BASE}/${complianceCheckFixture.id}`);
    expect(item.code).toBe(complianceCheckFixture.code);
  });

  it('gets by code with encoding', async () => {
    fetchMock.mockReturnValue(respond(complianceCheckFixture));
    await getComplianceCheckByCode('A/B 1');
    expect(String(fetchMock.mock.calls[0][0])).toBe(`${BASE}/by-code/A%2FB%201`);
  });

  it('posts create payloads as JSON', async () => {
    fetchMock.mockReturnValue(respond(complianceCheckFixture, 201));
    await createComplianceCheck(complianceCheckInput);
    const init = fetchMock.mock.calls[0][1] as RequestInit;
    expect(init.method).toBe('POST');
    expect(JSON.parse(String(init.body)).code).toBe(complianceCheckInput.code);
  });

  it('puts updates with expected version', async () => {
    fetchMock.mockReturnValue(respond(complianceCheckFixture));
    await updateComplianceCheck(complianceCheckFixture.id, { name: 'Renamed', expectedVersion: 3 });
    const init = fetchMock.mock.calls[0][1] as RequestInit;
    expect(init.method).toBe('PUT');
    expect(JSON.parse(String(init.body)).expectedVersion).toBe(3);
  });

  it('patches status through the query string', async () => {
    fetchMock.mockReturnValue(respond(complianceCheckFixture));
    await changeComplianceCheckStatus(complianceCheckFixture.id, 'MAINTENANCE');
    expect(String(fetchMock.mock.calls[0][0])).toContain('status=MAINTENANCE');
  });

  it('deletes and tolerates 204', async () => {
    fetchMock.mockReturnValue(respond(null, 204));
    await expect(deleteComplianceCheck(complianceCheckFixture.id)).resolves.toBeUndefined();
  });

  it('bulk creates', async () => {
    fetchMock.mockReturnValue(respond({ succeeded: [complianceCheckFixture], failures: [] }));
    const result = await bulkCreateComplianceChecks([complianceCheckInput]);
    expect(result.succeeded).toHaveLength(1);
  });

  it('surfaces api errors', async () => {
    fetchMock.mockReturnValue(respond({ message: 'Not found' }, 404));
    await expect(getComplianceCheck('missing')).rejects.toMatchObject({ status: 404 });
  });
});
