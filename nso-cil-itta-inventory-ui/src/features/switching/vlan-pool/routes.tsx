import type { RouteObject } from 'react-router-dom';
import { VlanPoolCreatePage, VlanPoolEditPage } from './VlanPoolEditPages';
import { VlanPoolDetailPage } from './VlanPoolDetailPage';
import { VlanPoolListPage } from './VlanPoolListPage';

export const vlanPoolRoutes: RouteObject[] = [
  { path: 'vlan-pools', element: <VlanPoolListPage /> },
  { path: 'vlan-pools/new', element: <VlanPoolCreatePage /> },
  { path: 'vlan-pools/:id', element: <VlanPoolDetailPage /> },
  { path: 'vlan-pools/:id/edit', element: <VlanPoolEditPage /> },
];
