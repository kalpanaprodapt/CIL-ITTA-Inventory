import type { RouteObject } from 'react-router-dom';
import { InterfaceCreatePage, InterfaceEditPage } from './InterfaceEditPages';
import { InterfaceDetailPage } from './InterfaceDetailPage';
import { InterfaceListPage } from './InterfaceListPage';

export const interfaceRoutes: RouteObject[] = [
  { path: 'interfaces', element: <InterfaceListPage /> },
  { path: 'interfaces/new', element: <InterfaceCreatePage /> },
  { path: 'interfaces/:id', element: <InterfaceDetailPage /> },
  { path: 'interfaces/:id/edit', element: <InterfaceEditPage /> },
];
