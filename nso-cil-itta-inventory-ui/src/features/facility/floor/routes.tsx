import type { RouteObject } from 'react-router-dom';
import { FloorCreatePage, FloorEditPage } from './FloorEditPages';
import { FloorDetailPage } from './FloorDetailPage';
import { FloorListPage } from './FloorListPage';

export const floorRoutes: RouteObject[] = [
  { path: 'floors', element: <FloorListPage /> },
  { path: 'floors/new', element: <FloorCreatePage /> },
  { path: 'floors/:id', element: <FloorDetailPage /> },
  { path: 'floors/:id/edit', element: <FloorEditPage /> },
];
