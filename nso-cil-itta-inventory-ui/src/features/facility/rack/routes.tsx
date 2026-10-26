import type { RouteObject } from 'react-router-dom';
import { RackCreatePage, RackEditPage } from './RackEditPages';
import { RackDetailPage } from './RackDetailPage';
import { RackListPage } from './RackListPage';

export const rackRoutes: RouteObject[] = [
  { path: 'racks', element: <RackListPage /> },
  { path: 'racks/new', element: <RackCreatePage /> },
  { path: 'racks/:id', element: <RackDetailPage /> },
  { path: 'racks/:id/edit', element: <RackEditPage /> },
];
