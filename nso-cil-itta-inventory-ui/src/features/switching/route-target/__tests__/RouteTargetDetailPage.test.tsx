import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { fireEvent, render, screen, waitFor } from '@testing-library/react';
import { MemoryRouter, Route, Routes } from 'react-router-dom';
import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest';
import { RouteTargetDetailPage } from '../RouteTargetDetailPage';
import { routeTargetFixture } from './fixtures';

function json(body: unknown, status = 200) {
  return Promise.resolve(new Response(JSON.stringify(body), { status, headers: { 'Content-Type': 'application/json' } }));
}

function renderPage() {
  const client = new QueryClient({ defaultOptions: { queries: { retry: false } } });
  return render(
    <QueryClientProvider client={client}>
      <MemoryRouter initialEntries={[`/switching/route-targets/${routeTargetFixture.id}`]}>
        <Routes>
          <Route path="/switching/route-targets/:id" element={<RouteTargetDetailPage />} />
          <Route path="/switching/route-targets" element={<div>list</div>} />
        </Routes>
      </MemoryRouter>
    </QueryClientProvider>,
  );
}

describe('RouteTargetDetailPage', () => {
  const fetchMock = vi.fn();

  beforeEach(() => {
    fetchMock.mockReset();
    fetchMock.mockImplementation(() => json(routeTargetFixture));
    vi.stubGlobal('fetch', fetchMock);
  });

  afterEach(() => {
    vi.unstubAllGlobals();
  });

  it('shows a spinner then the record', async () => {
    renderPage();
    expect(screen.getByRole('status')).toBeInTheDocument();
    expect(await screen.findByRole('heading', { name: routeTargetFixture.name })).toBeInTheDocument();
  });

  it('shows an error banner when loading fails', async () => {
    fetchMock.mockImplementation(() => json({ message: 'boom' }, 500));
    renderPage();
    expect(await screen.findByRole('alert')).toBeInTheDocument();
  });

  it('links to the edit page', async () => {
    renderPage();
    const link = await screen.findByRole('link', { name: 'Edit' });
    expect(link).toHaveAttribute('href', `/switching/route-targets/${routeTargetFixture.id}/edit`);
  });

  it('changes status through the status buttons', async () => {
    renderPage();
    fireEvent.click(await screen.findByRole('button', { name: 'MAINTENANCE' }));
    await waitFor(() =>
      expect(fetchMock.mock.calls.some(([url]) => String(url).includes('status=MAINTENANCE'))).toBe(true),
    );
  });

  it('asks for confirmation before deleting', async () => {
    renderPage();
    fireEvent.click(await screen.findByRole('button', { name: 'Delete' }));
    expect(screen.getByRole('dialog')).toBeInTheDocument();
    fireEvent.click(screen.getByRole('button', { name: 'Cancel' }));
    expect(screen.queryByRole('dialog')).not.toBeInTheDocument();
  });

  it('shows a row for every field', async () => {
    renderPage();
    await screen.findByRole('heading', { name: routeTargetFixture.name });
    for (const label of ['Code', 'Name', 'Description', 'Status', 'External Ref', 'Asn', 'Assigned Number', 'Rt Type', 'Vrf Id', 'Lifecycle Stage', 'Source System', 'Region', 'Owner', 'Criticality']) {
      expect(screen.getAllByText(label).length).toBeGreaterThan(0);
    }
  });
});
