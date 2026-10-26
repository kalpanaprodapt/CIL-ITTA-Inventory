import type { RouteObject } from 'react-router-dom';
import { IpSubnetCreatePage, IpSubnetEditPage } from './IpSubnetEditPages';
import { IpSubnetDetailPage } from './IpSubnetDetailPage';
import { IpSubnetListPage } from './IpSubnetListPage';

export const ipSubnetRoutes: RouteObject[] = [
  { path: 'ip-subnets', element: <IpSubnetListPage /> },
  { path: 'ip-subnets/new', element: <IpSubnetCreatePage /> },
  { path: 'ip-subnets/:id', element: <IpSubnetDetailPage /> },
  { path: 'ip-subnets/:id/edit', element: <IpSubnetEditPage /> },
];
