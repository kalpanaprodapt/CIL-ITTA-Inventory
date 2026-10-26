import type { RouteObject } from 'react-router-dom';
import { TopologyNodeCreatePage, TopologyNodeEditPage } from './TopologyNodeEditPages';
import { TopologyNodeDetailPage } from './TopologyNodeDetailPage';
import { TopologyNodeListPage } from './TopologyNodeListPage';

export const topologyNodeRoutes: RouteObject[] = [
  { path: 'topology-nodes', element: <TopologyNodeListPage /> },
  { path: 'topology-nodes/new', element: <TopologyNodeCreatePage /> },
  { path: 'topology-nodes/:id', element: <TopologyNodeDetailPage /> },
  { path: 'topology-nodes/:id/edit', element: <TopologyNodeEditPage /> },
];
