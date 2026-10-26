import type { RouteObject } from 'react-router-dom';
import { PathTraceCreatePage, PathTraceEditPage } from './PathTraceEditPages';
import { PathTraceDetailPage } from './PathTraceDetailPage';
import { PathTraceListPage } from './PathTraceListPage';

export const pathTraceRoutes: RouteObject[] = [
  { path: 'path-traces', element: <PathTraceListPage /> },
  { path: 'path-traces/new', element: <PathTraceCreatePage /> },
  { path: 'path-traces/:id', element: <PathTraceDetailPage /> },
  { path: 'path-traces/:id/edit', element: <PathTraceEditPage /> },
];
