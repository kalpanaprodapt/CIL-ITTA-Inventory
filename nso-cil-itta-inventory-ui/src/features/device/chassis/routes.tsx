import type { RouteObject } from 'react-router-dom';
import { ChassisCreatePage, ChassisEditPage } from './ChassisEditPages';
import { ChassisDetailPage } from './ChassisDetailPage';
import { ChassisListPage } from './ChassisListPage';

export const chassisRoutes: RouteObject[] = [
  { path: 'chassises', element: <ChassisListPage /> },
  { path: 'chassises/new', element: <ChassisCreatePage /> },
  { path: 'chassises/:id', element: <ChassisDetailPage /> },
  { path: 'chassises/:id/edit', element: <ChassisEditPage /> },
];
