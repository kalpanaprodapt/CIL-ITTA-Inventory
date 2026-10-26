import type { RouteObject } from 'react-router-dom';
import { UtilizationSampleCreatePage, UtilizationSampleEditPage } from './UtilizationSampleEditPages';
import { UtilizationSampleDetailPage } from './UtilizationSampleDetailPage';
import { UtilizationSampleListPage } from './UtilizationSampleListPage';

export const utilizationSampleRoutes: RouteObject[] = [
  { path: 'utilization-samples', element: <UtilizationSampleListPage /> },
  { path: 'utilization-samples/new', element: <UtilizationSampleCreatePage /> },
  { path: 'utilization-samples/:id', element: <UtilizationSampleDetailPage /> },
  { path: 'utilization-samples/:id/edit', element: <UtilizationSampleEditPage /> },
];
