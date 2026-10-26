import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { fireEvent, render, screen, waitFor } from '@testing-library/react';
import { MemoryRouter, Route, Routes } from 'react-router-dom';
import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest';
import { MaintenanceWindowCreatePage, MaintenanceWindowEditPage } from '../MaintenanceWindowEditPages';
import { maintenanceWindowFixture } from './fixtures';

function json(body: unknown, status = 200) {
  return Promise.resolve(new Response(JSON.stringify(body), { status, headers: { 'Content-Type': 'application/json' } }));
}

function wrap(path: string) {
  const client = new QueryClient({ defaultOptions: { queries: { retry: false }, mutations: { retry: false } } });
  return render(
    <QueryClientProvider client={client}>
      <MemoryRouter initialEntries={[path]}>
        <Routes>
          <Route path="/change/maintenance-windows/new" element={<MaintenanceWindowCreatePage />} />
          <Route path="/change/maintenance-windows/:id/edit" element={<MaintenanceWindowEditPage />} />
          <Route path="/change/maintenance-windows/:id" element={<div>detail</div>} />
          <Route path="/change/maintenance-windows" element={<div>list</div>} />
        </Routes>
      </MemoryRouter>
    </QueryClientProvider>,
  );
}

describe('MaintenanceWindow create and edit pages', () => {
  const fetchMock = vi.fn();

  beforeEach(() => {
    fetchMock.mockReset();
    vi.stubGlobal('fetch', fetchMock);
  });

  afterEach(() => {
    vi.unstubAllGlobals();
  });

  it('renders an empty create form', () => {
    wrap('/change/maintenance-windows/new');
    expect(screen.getByRole('heading', { name: 'New Maintenance Window' })).toBeInTheDocument();
    expect(screen.getByRole('button', { name: 'Create' })).toBeInTheDocument();
  });

  it('does not call the api when the form is invalid', async () => {
    wrap('/change/maintenance-windows/new');
    fireEvent.click(screen.getByRole('button', { name: 'Create' }));
    await waitFor(() => expect(screen.getAllByText(/is required/).length).toBeGreaterThan(0));
    expect(fetchMock).not.toHaveBeenCalled();
  });

  it('returns to the list on cancel', () => {
    wrap('/change/maintenance-windows/new');
    fireEvent.click(screen.getByRole('button', { name: 'Cancel' }));
    expect(screen.getByText('list')).toBeInTheDocument();
  });

  it('loads the record into the edit form', async () => {
    fetchMock.mockImplementation(() => json(maintenanceWindowFixture));
    wrap(`/change/maintenance-windows/${maintenanceWindowFixture.id}/edit`);
    expect(await screen.findByRole('heading', { name: `Edit ${maintenanceWindowFixture.code}` })).toBeInTheDocument();
    expect(screen.getByRole('button', { name: 'Save changes' })).toBeInTheDocument();
  });

  it('sends the expected version when saving', async () => {
    fetchMock.mockImplementation((_url: string, init?: RequestInit) =>
      init?.method === 'PUT' ? json(maintenanceWindowFixture) : json({ ...maintenanceWindowFixture, version: 7 }),
    );
    wrap(`/change/maintenance-windows/${maintenanceWindowFixture.id}/edit`);
    fireEvent.click(await screen.findByRole('button', { name: 'Save changes' }));
    await waitFor(() => expect(fetchMock.mock.calls.some(([, init]) => init?.method === 'PUT')).toBe(true));
    const put = fetchMock.mock.calls.find(([, init]) => init?.method === 'PUT');
    expect(JSON.parse(String(put?.[1]?.body)).expectedVersion).toBe(7);
  });

  it('shows the server message when saving fails', async () => {
    fetchMock.mockImplementation((_url: string, init?: RequestInit) =>
      init?.method === 'PUT' ? json({ message: 'Version conflict' }, 409) : json(maintenanceWindowFixture),
    );
    wrap(`/change/maintenance-windows/${maintenanceWindowFixture.id}/edit`);
    fireEvent.click(await screen.findByRole('button', { name: 'Save changes' }));
    expect(await screen.findByText('Version conflict')).toBeInTheDocument();
  });
});
