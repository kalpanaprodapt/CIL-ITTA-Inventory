import type { RouteObject } from 'react-router-dom';
import { TopologyEdgeCreatePage, TopologyEdgeEditPage } from './TopologyEdgeEditPages';
import { TopologyEdgeDetailPage } from './TopologyEdgeDetailPage';
import { TopologyEdgeListPage } from './TopologyEdgeListPage';

export const topologyEdgeRoutes: RouteObject[] = [
  { path: 'topology-edges', element: <TopologyEdgeListPage /> },
  { path: 'topology-edges/new', element: <TopologyEdgeCreatePage /> },
  { path: 'topology-edges/:id', element: <TopologyEdgeDetailPage /> },
  { path: 'topology-edges/:id/edit', element: <TopologyEdgeEditPage /> },
];
