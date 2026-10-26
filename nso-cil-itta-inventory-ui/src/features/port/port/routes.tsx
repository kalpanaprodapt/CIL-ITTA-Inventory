import type { RouteObject } from 'react-router-dom';
import { PortCreatePage, PortEditPage } from './PortEditPages';
import { PortDetailPage } from './PortDetailPage';
import { PortListPage } from './PortListPage';

export const portRoutes: RouteObject[] = [
  { path: 'ports', element: <PortListPage /> },
  { path: 'ports/new', element: <PortCreatePage /> },
  { path: 'ports/:id', element: <PortDetailPage /> },
  { path: 'ports/:id/edit', element: <PortEditPage /> },
];
