import type { RouteObject } from 'react-router-dom';
import { IpAddressCreatePage, IpAddressEditPage } from './IpAddressEditPages';
import { IpAddressDetailPage } from './IpAddressDetailPage';
import { IpAddressListPage } from './IpAddressListPage';

export const ipAddressRoutes: RouteObject[] = [
  { path: 'ip-addresses', element: <IpAddressListPage /> },
  { path: 'ip-addresses/new', element: <IpAddressCreatePage /> },
  { path: 'ip-addresses/:id', element: <IpAddressDetailPage /> },
  { path: 'ip-addresses/:id/edit', element: <IpAddressEditPage /> },
];
