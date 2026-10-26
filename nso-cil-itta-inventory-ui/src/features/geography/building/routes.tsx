import type { RouteObject } from 'react-router-dom';
import { BuildingCreatePage, BuildingEditPage } from './BuildingEditPages';
import { BuildingDetailPage } from './BuildingDetailPage';
import { BuildingListPage } from './BuildingListPage';

export const buildingRoutes: RouteObject[] = [
  { path: 'buildings', element: <BuildingListPage /> },
  { path: 'buildings/new', element: <BuildingCreatePage /> },
  { path: 'buildings/:id', element: <BuildingDetailPage /> },
  { path: 'buildings/:id/edit', element: <BuildingEditPage /> },
];
