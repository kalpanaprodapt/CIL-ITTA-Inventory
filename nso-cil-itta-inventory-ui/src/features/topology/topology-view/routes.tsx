import type { RouteObject } from 'react-router-dom';
import { TopologyViewCreatePage, TopologyViewEditPage } from './TopologyViewEditPages';
import { TopologyViewDetailPage } from './TopologyViewDetailPage';
import { TopologyViewListPage } from './TopologyViewListPage';

export const topologyViewRoutes: RouteObject[] = [
  { path: 'topology-views', element: <TopologyViewListPage /> },
  { path: 'topology-views/new', element: <TopologyViewCreatePage /> },
  { path: 'topology-views/:id', element: <TopologyViewDetailPage /> },
  { path: 'topology-views/:id/edit', element: <TopologyViewEditPage /> },
];
