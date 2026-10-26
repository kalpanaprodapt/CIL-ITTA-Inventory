import type { RouteObject } from 'react-router-dom';
import { BgpSessionCreatePage, BgpSessionEditPage } from './BgpSessionEditPages';
import { BgpSessionDetailPage } from './BgpSessionDetailPage';
import { BgpSessionListPage } from './BgpSessionListPage';

export const bgpSessionRoutes: RouteObject[] = [
  { path: 'bgp-sessions', element: <BgpSessionListPage /> },
  { path: 'bgp-sessions/new', element: <BgpSessionCreatePage /> },
  { path: 'bgp-sessions/:id', element: <BgpSessionDetailPage /> },
  { path: 'bgp-sessions/:id/edit', element: <BgpSessionEditPage /> },
];
