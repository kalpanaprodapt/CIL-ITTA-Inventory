import type { RouteObject } from 'react-router-dom';
import { FiberStrandCreatePage, FiberStrandEditPage } from './FiberStrandEditPages';
import { FiberStrandDetailPage } from './FiberStrandDetailPage';
import { FiberStrandListPage } from './FiberStrandListPage';

export const fiberStrandRoutes: RouteObject[] = [
  { path: 'fiber-strands', element: <FiberStrandListPage /> },
  { path: 'fiber-strands/new', element: <FiberStrandCreatePage /> },
  { path: 'fiber-strands/:id', element: <FiberStrandDetailPage /> },
  { path: 'fiber-strands/:id/edit', element: <FiberStrandEditPage /> },
];
