import type { RouteObject } from 'react-router-dom';
import { CircuitTerminationCreatePage, CircuitTerminationEditPage } from './CircuitTerminationEditPages';
import { CircuitTerminationDetailPage } from './CircuitTerminationDetailPage';
import { CircuitTerminationListPage } from './CircuitTerminationListPage';

export const circuitTerminationRoutes: RouteObject[] = [
  { path: 'circuit-terminations', element: <CircuitTerminationListPage /> },
  { path: 'circuit-terminations/new', element: <CircuitTerminationCreatePage /> },
  { path: 'circuit-terminations/:id', element: <CircuitTerminationDetailPage /> },
  { path: 'circuit-terminations/:id/edit', element: <CircuitTerminationEditPage /> },
];
