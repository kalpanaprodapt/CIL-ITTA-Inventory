import type { RouteObject } from 'react-router-dom';
import { CircuitSegmentCreatePage, CircuitSegmentEditPage } from './CircuitSegmentEditPages';
import { CircuitSegmentDetailPage } from './CircuitSegmentDetailPage';
import { CircuitSegmentListPage } from './CircuitSegmentListPage';

export const circuitSegmentRoutes: RouteObject[] = [
  { path: 'circuit-segments', element: <CircuitSegmentListPage /> },
  { path: 'circuit-segments/new', element: <CircuitSegmentCreatePage /> },
  { path: 'circuit-segments/:id', element: <CircuitSegmentDetailPage /> },
  { path: 'circuit-segments/:id/edit', element: <CircuitSegmentEditPage /> },
];
