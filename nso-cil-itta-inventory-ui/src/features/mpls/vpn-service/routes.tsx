import type { RouteObject } from 'react-router-dom';
import { VpnServiceCreatePage, VpnServiceEditPage } from './VpnServiceEditPages';
import { VpnServiceDetailPage } from './VpnServiceDetailPage';
import { VpnServiceListPage } from './VpnServiceListPage';

export const vpnServiceRoutes: RouteObject[] = [
  { path: 'vpn-services', element: <VpnServiceListPage /> },
  { path: 'vpn-services/new', element: <VpnServiceCreatePage /> },
  { path: 'vpn-services/:id', element: <VpnServiceDetailPage /> },
  { path: 'vpn-services/:id/edit', element: <VpnServiceEditPage /> },
];
