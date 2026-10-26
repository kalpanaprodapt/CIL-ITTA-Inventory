import type { RouteObject } from 'react-router-dom';
import { CircuitProviderCreatePage, CircuitProviderEditPage } from './CircuitProviderEditPages';
import { CircuitProviderDetailPage } from './CircuitProviderDetailPage';
import { CircuitProviderListPage } from './CircuitProviderListPage';

export const circuitProviderRoutes: RouteObject[] = [
  { path: 'circuit-providers', element: <CircuitProviderListPage /> },
  { path: 'circuit-providers/new', element: <CircuitProviderCreatePage /> },
  { path: 'circuit-providers/:id', element: <CircuitProviderDetailPage /> },
  { path: 'circuit-providers/:id/edit', element: <CircuitProviderEditPage /> },
];
