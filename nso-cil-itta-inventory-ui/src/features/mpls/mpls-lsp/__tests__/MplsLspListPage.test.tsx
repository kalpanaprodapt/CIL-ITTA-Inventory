import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { render, screen, waitFor } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest';
import { MplsLspListPage } from '../MplsLspListPage';
import { mplsLspFixture, mplsLspPage } from './fixtures';

function renderPage() {
  const client = new QueryClient({ defaultOptions: { queries: { retry: false } } });
  return render(
    <QueryClientProvider client={client}>
      <MemoryRouter>
        <MplsLspListPage />
      </MemoryRouter>
    </QueryClientProvider>,
  );
}

describe('MplsLspListPage', () => {
  const fetchMock = vi.fn();

  beforeEach(() => {
    fetchMock.mockReset();
    vi.stubGlobal('fetch', fetchMock);
  });

  afterEach(() => {
    vi.unstubAllGlobals();
  });

  function json(body: unknown, status = 200) {
    return Promise.resolve(new Response(JSON.stringify(body), { status, headers: { 'Content-Type': 'application/json' } }));
  }

  it('lists records', async () => {
    fetchMock.mockImplementation((url: string) =>
      url.includes('/stats/status') ? json({ ACTIVE: 1 }) : json(mplsLspPage),
    );
    renderPage();
    await waitFor(() => expect(screen.getByText(mplsLspFixture.code)).toBeInTheDocument());
  });

  it('shows the empty state', async () => {
    fetchMock.mockImplementation((url: string) =>
      url.includes('/stats/status')
        ? json({})
        : json({ content: [], page: 0, size: 20, totalElements: 0, totalPages: 0 }),
    );
    renderPage();
    await waitFor(() => expect(screen.getByText('No Mpls Lsp records')).toBeInTheDocument());
  });

  it('shows an error banner when the request fails', async () => {
    fetchMock.mockImplementation(() => json({ message: 'boom' }, 500));
    renderPage();
    await waitFor(() => expect(screen.getByRole('alert')).toBeInTheDocument());
  });

  it('offers CSV export and creation', async () => {
    fetchMock.mockImplementation(() => json(mplsLspPage));
    renderPage();
    expect(screen.getByRole('button', { name: 'Export CSV' })).toBeInTheDocument();
    expect(screen.getByRole('link', { name: 'New Mpls Lsp' })).toHaveAttribute('href', '/mpls/mpls-lsps/new');
  });
});
