import type { RouteObject } from 'react-router-dom';
import { SubInterfaceCreatePage, SubInterfaceEditPage } from './SubInterfaceEditPages';
import { SubInterfaceDetailPage } from './SubInterfaceDetailPage';
import { SubInterfaceListPage } from './SubInterfaceListPage';

export const subInterfaceRoutes: RouteObject[] = [
  { path: 'sub-interfaces', element: <SubInterfaceListPage /> },
  { path: 'sub-interfaces/new', element: <SubInterfaceCreatePage /> },
  { path: 'sub-interfaces/:id', element: <SubInterfaceDetailPage /> },
  { path: 'sub-interfaces/:id/edit', element: <SubInterfaceEditPage /> },
];
