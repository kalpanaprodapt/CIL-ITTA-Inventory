import type { RouteObject } from 'react-router-dom';
import { CircuitOrderCreatePage, CircuitOrderEditPage } from './CircuitOrderEditPages';
import { CircuitOrderDetailPage } from './CircuitOrderDetailPage';
import { CircuitOrderListPage } from './CircuitOrderListPage';

export const circuitOrderRoutes: RouteObject[] = [
  { path: 'circuit-orders', element: <CircuitOrderListPage /> },
  { path: 'circuit-orders/new', element: <CircuitOrderCreatePage /> },
  { path: 'circuit-orders/:id', element: <CircuitOrderDetailPage /> },
  { path: 'circuit-orders/:id/edit', element: <CircuitOrderEditPage /> },
];
