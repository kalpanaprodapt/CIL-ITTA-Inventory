import type { RouteObject } from 'react-router-dom';
import { AlarmInstanceCreatePage, AlarmInstanceEditPage } from './AlarmInstanceEditPages';
import { AlarmInstanceDetailPage } from './AlarmInstanceDetailPage';
import { AlarmInstanceListPage } from './AlarmInstanceListPage';

export const alarmInstanceRoutes: RouteObject[] = [
  { path: 'alarm-instances', element: <AlarmInstanceListPage /> },
  { path: 'alarm-instances/new', element: <AlarmInstanceCreatePage /> },
  { path: 'alarm-instances/:id', element: <AlarmInstanceDetailPage /> },
  { path: 'alarm-instances/:id/edit', element: <AlarmInstanceEditPage /> },
];
