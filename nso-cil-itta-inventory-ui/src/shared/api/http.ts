import { getAccessToken } from '@/shared/auth/token';

export class ApiError extends Error {
  status: number;
  details: string[];

  constructor(status: number, message: string, details: string[] = []) {
    super(message);
    this.status = status;
    this.details = details;
  }
}

type Params = Record<string, string | number | boolean | undefined | null>;

const BASE = import.meta.env?.VITE_API_BASE ?? '';

function buildUrl(path: string, params?: Params): string {
  if (!params) return BASE + path;
  const search = new URLSearchParams();
  for (const [key, value] of Object.entries(params)) {
    if (value !== undefined && value !== null && value !== '') {
      search.set(key, String(value));
    }
  }
  const qs = search.toString();
  return BASE + path + (qs ? `?${qs}` : '');
}

async function request(method: string, path: string, params?: Params, body?: unknown): Promise<Response> {
  const headers: Record<string, string> = { Accept: 'application/json' };
  const token = getAccessToken();
  if (token) headers.Authorization = `Bearer ${token}`;
  const tenant = sessionStorage.getItem('itta.tenant');
  if (tenant) headers['X-Tenant-Id'] = tenant;
  let payload: string | undefined;
  if (body !== undefined) {
    headers['Content-Type'] = 'application/json';
    payload = JSON.stringify(body);
  }
  const response = await fetch(buildUrl(path, params), { method, headers, body: payload });
  if (!response.ok) {
    let message = response.statusText || 'Request failed';
    let details: string[] = [];
    try {
      const data = await response.json();
      message = data.message ?? message;
      details = data.details ?? [];
    } catch {
      /* body was not JSON */
    }
    throw new ApiError(response.status, message, details);
  }
  return response;
}

async function json<T>(response: Response): Promise<T> {
  if (response.status === 204) return undefined as T;
  const text = await response.text();
  return (text ? JSON.parse(text) : undefined) as T;
}

export const http = {
  get: async <T>(path: string, params?: Params) => json<T>(await request('GET', path, params)),
  post: async <T>(path: string, body?: unknown, params?: Params) => json<T>(await request('POST', path, params, body)),
  put: async <T>(path: string, body?: unknown, params?: Params) => json<T>(await request('PUT', path, params, body)),
  patch: async <T>(path: string, body?: unknown, params?: Params) => json<T>(await request('PATCH', path, params, body)),
  delete: async (path: string) => {
    await request('DELETE', path);
  },
  blob: async (path: string, params?: Params) => (await request('GET', path, params)).blob(),
};

export function errorMessage(err: unknown): string {
  if (err instanceof ApiError) {
    return err.details.length ? `${err.message}: ${err.details.join('; ')}` : err.message;
  }
  return err instanceof Error ? err.message : 'Unexpected error';
}
