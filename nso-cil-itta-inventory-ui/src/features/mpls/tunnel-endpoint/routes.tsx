import type { RouteObject } from 'react-router-dom';
import { TunnelEndpointCreatePage, TunnelEndpointEditPage } from './TunnelEndpointEditPages';
import { TunnelEndpointDetailPage } from './TunnelEndpointDetailPage';
import { TunnelEndpointListPage } from './TunnelEndpointListPage';

export const tunnelEndpointRoutes: RouteObject[] = [
  { path: 'tunnel-endpoints', element: <TunnelEndpointListPage /> },
  { path: 'tunnel-endpoints/new', element: <TunnelEndpointCreatePage /> },
  { path: 'tunnel-endpoints/:id', element: <TunnelEndpointDetailPage /> },
  { path: 'tunnel-endpoints/:id/edit', element: <TunnelEndpointEditPage /> },
];
