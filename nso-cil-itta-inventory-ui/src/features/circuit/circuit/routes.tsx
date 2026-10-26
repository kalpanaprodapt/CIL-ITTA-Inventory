import type { RouteObject } from 'react-router-dom';
import { CircuitCreatePage, CircuitEditPage } from './CircuitEditPages';
import { CircuitDetailPage } from './CircuitDetailPage';
import { CircuitListPage } from './CircuitListPage';

export const circuitRoutes: RouteObject[] = [
  { path: 'circuits', element: <CircuitListPage /> },
  { path: 'circuits/new', element: <CircuitCreatePage /> },
  { path: 'circuits/:id', element: <CircuitDetailPage /> },
  { path: 'circuits/:id/edit', element: <CircuitEditPage /> },
];
