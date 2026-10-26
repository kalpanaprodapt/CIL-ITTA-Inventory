import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { fireEvent, render, screen, waitFor } from '@testing-library/react';
import { MemoryRouter, Route, Routes } from 'react-router-dom';
import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest';
import { FiberStrandDetailPage } from '../FiberStrandDetailPage';
import { fiberStrandFixture } from './fixtures';

function json(body: unknown, status = 200) {
  return Promise.resolve(new Response(JSON.stringify(body), { status, headers: { 'Content-Type': 'application/json' } }));
}

function renderPage() {
  const client = new QueryClient({ defaultOptions: { queries: { retry: false } } });
  return render(
    <QueryClientProvider client={client}>
      <MemoryRouter initialEntries={[`/cabling/fiber-strands/${fiberStrandFixture.id}`]}>
        <Routes>
          <Route path="/cabling/fiber-strands/:id" element={<FiberStrandDetailPage />} />
          <Route path="/cabling/fiber-strands" element={<div>list</div>} />
        </Routes>
      </MemoryRouter>
    </QueryClientProvider>,
  );
}

describe('FiberStrandDetailPage', () => {
  const fetchMock = vi.fn();

  beforeEach(() => {
    fetchMock.mockReset();
    fetchMock.mockImplementation(() => json(fiberStrandFixture));
    vi.stubGlobal('fetch', fetchMock);
  });

  afterEach(() => {
    vi.unstubAllGlobals();
  });

  it('shows a spinner then the record', async () => {
    renderPage();
    expect(screen.getByRole('status')).toBeInTheDocument();
    expect(await screen.findByRole('heading', { name: fiberStrandFixture.name })).toBeInTheDocument();
  });

  it('shows an error banner when loading fails', async () => {
    fetchMock.mockImplementation(() => json({ message: 'boom' }, 500));
    renderPage();
    expect(await screen.findByRole('alert')).toBeInTheDocument();
  });

  it('links to the edit page', async () => {
    renderPage();
    const link = await screen.findByRole('link', { name: 'Edit' });
    expect(link).toHaveAttribute('href', `/cabling/fiber-strands/${fiberStrandFixture.id}/edit`);
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
    await screen.findByRole('heading', { name: fiberStrandFixture.name });
    for (const label of ['Code', 'Name', 'Description', 'Status', 'External Ref', 'Cable Id', 'Strand Number', 'Buffer Tube', 'Tube Color', 'Attenuation Db', 'In Use', 'Region', 'Owner', 'Criticality', 'Cost Center', 'Notes']) {
      expect(screen.getAllByText(label).length).toBeGreaterThan(0);
    }
  });
});
