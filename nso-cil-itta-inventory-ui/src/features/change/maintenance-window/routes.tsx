import type { RouteObject } from 'react-router-dom';
import { MaintenanceWindowCreatePage, MaintenanceWindowEditPage } from './MaintenanceWindowEditPages';
import { MaintenanceWindowDetailPage } from './MaintenanceWindowDetailPage';
import { MaintenanceWindowListPage } from './MaintenanceWindowListPage';

export const maintenanceWindowRoutes: RouteObject[] = [
  { path: 'maintenance-windows', element: <MaintenanceWindowListPage /> },
  { path: 'maintenance-windows/new', element: <MaintenanceWindowCreatePage /> },
  { path: 'maintenance-windows/:id', element: <MaintenanceWindowDetailPage /> },
  { path: 'maintenance-windows/:id/edit', element: <MaintenanceWindowEditPage /> },
];
