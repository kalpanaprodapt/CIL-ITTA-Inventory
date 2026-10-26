import type { RouteObject } from 'react-router-dom';
import { DepreciationScheduleCreatePage, DepreciationScheduleEditPage } from './DepreciationScheduleEditPages';
import { DepreciationScheduleDetailPage } from './DepreciationScheduleDetailPage';
import { DepreciationScheduleListPage } from './DepreciationScheduleListPage';

export const depreciationScheduleRoutes: RouteObject[] = [
  { path: 'depreciation-schedules', element: <DepreciationScheduleListPage /> },
  { path: 'depreciation-schedules/new', element: <DepreciationScheduleCreatePage /> },
  { path: 'depreciation-schedules/:id', element: <DepreciationScheduleDetailPage /> },
  { path: 'depreciation-schedules/:id/edit', element: <DepreciationScheduleEditPage /> },
];
