import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { fireEvent, render, screen, waitFor } from '@testing-library/react';
import { MemoryRouter, Route, Routes } from 'react-router-dom';
import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest';
import { WebhookSubscriptionCreatePage, WebhookSubscriptionEditPage } from '../WebhookSubscriptionEditPages';
import { webhookSubscriptionFixture } from './fixtures';

function json(body: unknown, status = 200) {
  return Promise.resolve(new Response(JSON.stringify(body), { status, headers: { 'Content-Type': 'application/json' } }));
}

function wrap(path: string) {
  const client = new QueryClient({ defaultOptions: { queries: { retry: false }, mutations: { retry: false } } });
  return render(
    <QueryClientProvider client={client}>
      <MemoryRouter initialEntries={[path]}>
        <Routes>
          <Route path="/integration/webhook-subscriptions/new" element={<WebhookSubscriptionCreatePage />} />
          <Route path="/integration/webhook-subscriptions/:id/edit" element={<WebhookSubscriptionEditPage />} />
          <Route path="/integration/webhook-subscriptions/:id" element={<div>detail</div>} />
          <Route path="/integration/webhook-subscriptions" element={<div>list</div>} />
        </Routes>
      </MemoryRouter>
    </QueryClientProvider>,
  );
}

describe('WebhookSubscription create and edit pages', () => {
  const fetchMock = vi.fn();

  beforeEach(() => {
    fetchMock.mockReset();
    vi.stubGlobal('fetch', fetchMock);
  });

  afterEach(() => {
    vi.unstubAllGlobals();
  });

  it('renders an empty create form', () => {
    wrap('/integration/webhook-subscriptions/new');
    expect(screen.getByRole('heading', { name: 'New Webhook Subscription' })).toBeInTheDocument();
    expect(screen.getByRole('button', { name: 'Create' })).toBeInTheDocument();
  });

  it('does not call the api when the form is invalid', async () => {
    wrap('/integration/webhook-subscriptions/new');
    fireEvent.click(screen.getByRole('button', { name: 'Create' }));
    await waitFor(() => expect(screen.getAllByText(/is required/).length).toBeGreaterThan(0));
    expect(fetchMock).not.toHaveBeenCalled();
  });

  it('returns to the list on cancel', () => {
    wrap('/integration/webhook-subscriptions/new');
    fireEvent.click(screen.getByRole('button', { name: 'Cancel' }));
    expect(screen.getByText('list')).toBeInTheDocument();
  });

  it('loads the record into the edit form', async () => {
    fetchMock.mockImplementation(() => json(webhookSubscriptionFixture));
    wrap(`/integration/webhook-subscriptions/${webhookSubscriptionFixture.id}/edit`);
    expect(await screen.findByRole('heading', { name: `Edit ${webhookSubscriptionFixture.code}` })).toBeInTheDocument();
    expect(screen.getByRole('button', { name: 'Save changes' })).toBeInTheDocument();
  });

  it('sends the expected version when saving', async () => {
    fetchMock.mockImplementation((_url: string, init?: RequestInit) =>
      init?.method === 'PUT' ? json(webhookSubscriptionFixture) : json({ ...webhookSubscriptionFixture, version: 7 }),
    );
    wrap(`/integration/webhook-subscriptions/${webhookSubscriptionFixture.id}/edit`);
    fireEvent.click(await screen.findByRole('button', { name: 'Save changes' }));
    await waitFor(() => expect(fetchMock.mock.calls.some(([, init]) => init?.method === 'PUT')).toBe(true));
    const put = fetchMock.mock.calls.find(([, init]) => init?.method === 'PUT');
    expect(JSON.parse(String(put?.[1]?.body)).expectedVersion).toBe(7);
  });

  it('shows the server message when saving fails', async () => {
    fetchMock.mockImplementation((_url: string, init?: RequestInit) =>
      init?.method === 'PUT' ? json({ message: 'Version conflict' }, 409) : json(webhookSubscriptionFixture),
    );
    wrap(`/integration/webhook-subscriptions/${webhookSubscriptionFixture.id}/edit`);
    fireEvent.click(await screen.findByRole('button', { name: 'Save changes' }));
    expect(await screen.findByText('Version conflict')).toBeInTheDocument();
  });
});
