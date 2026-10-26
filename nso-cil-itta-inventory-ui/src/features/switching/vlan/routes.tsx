import type { RouteObject } from 'react-router-dom';
import { VlanCreatePage, VlanEditPage } from './VlanEditPages';
import { VlanDetailPage } from './VlanDetailPage';
import { VlanListPage } from './VlanListPage';

export const vlanRoutes: RouteObject[] = [
  { path: 'vlans', element: <VlanListPage /> },
  { path: 'vlans/new', element: <VlanCreatePage /> },
  { path: 'vlans/:id', element: <VlanDetailPage /> },
  { path: 'vlans/:id/edit', element: <VlanEditPage /> },
];
