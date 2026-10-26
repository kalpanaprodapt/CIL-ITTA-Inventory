import type { RouteObject } from 'react-router-dom';
import { NetworkDeviceCreatePage, NetworkDeviceEditPage } from './NetworkDeviceEditPages';
import { NetworkDeviceDetailPage } from './NetworkDeviceDetailPage';
import { NetworkDeviceListPage } from './NetworkDeviceListPage';

export const networkDeviceRoutes: RouteObject[] = [
  { path: 'network-devices', element: <NetworkDeviceListPage /> },
  { path: 'network-devices/new', element: <NetworkDeviceCreatePage /> },
  { path: 'network-devices/:id', element: <NetworkDeviceDetailPage /> },
  { path: 'network-devices/:id/edit', element: <NetworkDeviceEditPage /> },
];
