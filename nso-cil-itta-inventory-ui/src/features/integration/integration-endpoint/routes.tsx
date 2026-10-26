import type { RouteObject } from 'react-router-dom';
import { IntegrationEndpointCreatePage, IntegrationEndpointEditPage } from './IntegrationEndpointEditPages';
import { IntegrationEndpointDetailPage } from './IntegrationEndpointDetailPage';
import { IntegrationEndpointListPage } from './IntegrationEndpointListPage';

export const integrationEndpointRoutes: RouteObject[] = [
  { path: 'integration-endpoints', element: <IntegrationEndpointListPage /> },
  { path: 'integration-endpoints/new', element: <IntegrationEndpointCreatePage /> },
  { path: 'integration-endpoints/:id', element: <IntegrationEndpointDetailPage /> },
  { path: 'integration-endpoints/:id/edit', element: <IntegrationEndpointEditPage /> },
];
