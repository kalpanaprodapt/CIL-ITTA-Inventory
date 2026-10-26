import type { RouteObject } from 'react-router-dom';
import { IpPoolCreatePage, IpPoolEditPage } from './IpPoolEditPages';
import { IpPoolDetailPage } from './IpPoolDetailPage';
import { IpPoolListPage } from './IpPoolListPage';

export const ipPoolRoutes: RouteObject[] = [
  { path: 'ip-pools', element: <IpPoolListPage /> },
  { path: 'ip-pools/new', element: <IpPoolCreatePage /> },
  { path: 'ip-pools/:id', element: <IpPoolDetailPage /> },
  { path: 'ip-pools/:id/edit', element: <IpPoolEditPage /> },
];
