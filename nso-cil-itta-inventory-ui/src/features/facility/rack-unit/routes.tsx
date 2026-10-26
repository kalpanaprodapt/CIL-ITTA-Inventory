import type { RouteObject } from 'react-router-dom';
import { RackUnitCreatePage, RackUnitEditPage } from './RackUnitEditPages';
import { RackUnitDetailPage } from './RackUnitDetailPage';
import { RackUnitListPage } from './RackUnitListPage';

export const rackUnitRoutes: RouteObject[] = [
  { path: 'rack-units', element: <RackUnitListPage /> },
  { path: 'rack-units/new', element: <RackUnitCreatePage /> },
  { path: 'rack-units/:id', element: <RackUnitDetailPage /> },
  { path: 'rack-units/:id/edit', element: <RackUnitEditPage /> },
];
