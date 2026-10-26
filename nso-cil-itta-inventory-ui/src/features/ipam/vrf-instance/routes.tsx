import type { RouteObject } from 'react-router-dom';
import { VrfInstanceCreatePage, VrfInstanceEditPage } from './VrfInstanceEditPages';
import { VrfInstanceDetailPage } from './VrfInstanceDetailPage';
import { VrfInstanceListPage } from './VrfInstanceListPage';

export const vrfInstanceRoutes: RouteObject[] = [
  { path: 'vrf-instances', element: <VrfInstanceListPage /> },
  { path: 'vrf-instances/new', element: <VrfInstanceCreatePage /> },
  { path: 'vrf-instances/:id', element: <VrfInstanceDetailPage /> },
  { path: 'vrf-instances/:id/edit', element: <VrfInstanceEditPage /> },
];
