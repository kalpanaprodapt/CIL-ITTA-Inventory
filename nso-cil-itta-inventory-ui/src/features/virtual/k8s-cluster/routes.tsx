import type { RouteObject } from 'react-router-dom';
import { K8sClusterCreatePage, K8sClusterEditPage } from './K8sClusterEditPages';
import { K8sClusterDetailPage } from './K8sClusterDetailPage';
import { K8sClusterListPage } from './K8sClusterListPage';

export const k8sClusterRoutes: RouteObject[] = [
  { path: 'k8s-clusters', element: <K8sClusterListPage /> },
  { path: 'k8s-clusters/new', element: <K8sClusterCreatePage /> },
  { path: 'k8s-clusters/:id', element: <K8sClusterDetailPage /> },
  { path: 'k8s-clusters/:id/edit', element: <K8sClusterEditPage /> },
];
