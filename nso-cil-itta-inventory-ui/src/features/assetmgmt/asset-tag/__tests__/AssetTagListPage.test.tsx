import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { render, screen, waitFor } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest';
import { AssetTagListPage } from '../AssetTagListPage';
import { assetTagFixture, assetTagPage } from './fixtures';

function renderPage() {
  const client = new QueryClient({ defaultOptions: { queries: { retry: false } } });
  return render(
    <QueryClientProvider client={client}>
      <MemoryRouter>
        <AssetTagListPage />
      </MemoryRouter>
    </QueryClientProvider>,
  );
}

describe('AssetTagListPage', () => {
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
      url.includes('/stats/status') ? json({ ACTIVE: 1 }) : json(assetTagPage),
    );
    renderPage();
    await waitFor(() => expect(screen.getByText(assetTagFixture.code)).toBeInTheDocument());
  });

  it('shows the empty state', async () => {
    fetchMock.mockImplementation((url: string) =>
      url.includes('/stats/status')
        ? json({})
        : json({ content: [], page: 0, size: 20, totalElements: 0, totalPages: 0 }),
    );
    renderPage();
    await waitFor(() => expect(screen.getByText('No Asset Tag records')).toBeInTheDocument());
  });

  it('shows an error banner when the request fails', async () => {
    fetchMock.mockImplementation(() => json({ message: 'boom' }, 500));
    renderPage();
    await waitFor(() => expect(screen.getByRole('alert')).toBeInTheDocument());
  });

  it('offers CSV export and creation', async () => {
    fetchMock.mockImplementation(() => json(assetTagPage));
    renderPage();
    expect(screen.getByRole('button', { name: 'Export CSV' })).toBeInTheDocument();
    expect(screen.getByRole('link', { name: 'New Asset Tag' })).toHaveAttribute('href', '/assetmgmt/asset-tags/new');
  });
});
