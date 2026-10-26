import type { RouteObject } from 'react-router-dom';
import { WebhookSubscriptionCreatePage, WebhookSubscriptionEditPage } from './WebhookSubscriptionEditPages';
import { WebhookSubscriptionDetailPage } from './WebhookSubscriptionDetailPage';
import { WebhookSubscriptionListPage } from './WebhookSubscriptionListPage';

export const webhookSubscriptionRoutes: RouteObject[] = [
  { path: 'webhook-subscriptions', element: <WebhookSubscriptionListPage /> },
  { path: 'webhook-subscriptions/new', element: <WebhookSubscriptionCreatePage /> },
  { path: 'webhook-subscriptions/:id', element: <WebhookSubscriptionDetailPage /> },
  { path: 'webhook-subscriptions/:id/edit', element: <WebhookSubscriptionEditPage /> },
];
