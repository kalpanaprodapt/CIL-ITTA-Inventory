import type { RouteObject } from 'react-router-dom';
import { LinkCreatePage, LinkEditPage } from './LinkEditPages';
import { LinkDetailPage } from './LinkDetailPage';
import { LinkListPage } from './LinkListPage';

export const linkRoutes: RouteObject[] = [
  { path: 'links', element: <LinkListPage /> },
  { path: 'links/new', element: <LinkCreatePage /> },
  { path: 'links/:id', element: <LinkDetailPage /> },
  { path: 'links/:id/edit', element: <LinkEditPage /> },
];
