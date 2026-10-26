import type { RouteObject } from 'react-router-dom';
import { HypervisorCreatePage, HypervisorEditPage } from './HypervisorEditPages';
import { HypervisorDetailPage } from './HypervisorDetailPage';
import { HypervisorListPage } from './HypervisorListPage';

export const hypervisorRoutes: RouteObject[] = [
  { path: 'hypervisors', element: <HypervisorListPage /> },
  { path: 'hypervisors/new', element: <HypervisorCreatePage /> },
  { path: 'hypervisors/:id', element: <HypervisorDetailPage /> },
  { path: 'hypervisors/:id/edit', element: <HypervisorEditPage /> },
];
