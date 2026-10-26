import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { fireEvent, render, screen, waitFor } from '@testing-library/react';
import { MemoryRouter, Route, Routes } from 'react-router-dom';
import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest';
import { ComplianceCheckDetailPage } from '../ComplianceCheckDetailPage';
import { complianceCheckFixture } from './fixtures';

function json(body: unknown, status = 200) {
  return Promise.resolve(new Response(JSON.stringify(body), { status, headers: { 'Content-Type': 'application/json' } }));
}

function renderPage() {
  const client = new QueryClient({ defaultOptions: { queries: { retry: false } } });
  return render(
    <QueryClientProvider client={client}>
      <MemoryRouter initialEntries={[`/compliance/compliance-checks/${complianceCheckFixture.id}`]}>
        <Routes>
          <Route path="/compliance/compliance-checks/:id" element={<ComplianceCheckDetailPage />} />
          <Route path="/compliance/compliance-checks" element={<div>list</div>} />
        </Routes>
      </MemoryRouter>
    </QueryClientProvider>,
  );
}

describe('ComplianceCheckDetailPage', () => {
  const fetchMock = vi.fn();

  beforeEach(() => {
    fetchMock.mockReset();
    fetchMock.mockImplementation(() => json(complianceCheckFixture));
    vi.stubGlobal('fetch', fetchMock);
  });

  afterEach(() => {
    vi.unstubAllGlobals();
  });

  it('shows a spinner then the record', async () => {
    renderPage();
    expect(screen.getByRole('status')).toBeInTheDocument();
    expect(await screen.findByRole('heading', { name: complianceCheckFixture.name })).toBeInTheDocument();
  });

  it('shows an error banner when loading fails', async () => {
    fetchMock.mockImplementation(() => json({ message: 'boom' }, 500));
    renderPage();
    expect(await screen.findByRole('alert')).toBeInTheDocument();
  });

  it('links to the edit page', async () => {
    renderPage();
    const link = await screen.findByRole('link', { name: 'Edit' });
    expect(link).toHaveAttribute('href', `/compliance/compliance-checks/${complianceCheckFixture.id}/edit`);
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
    await screen.findByRole('heading', { name: complianceCheckFixture.name });
    for (const label of ['Code', 'Name', 'Description', 'Status', 'External Ref', 'Rule Id', 'Target Ref', 'Checked At', 'Result', 'Evidence', 'Criticality', 'Cost Center', 'Notes', 'Effective From', 'Effective To']) {
      expect(screen.getAllByText(label).length).toBeGreaterThan(0);
    }
  });
});
