import type { RouteObject } from 'react-router-dom';
import { SiteCreatePage, SiteEditPage } from './SiteEditPages';
import { SiteDetailPage } from './SiteDetailPage';
import { SiteListPage } from './SiteListPage';

export const siteRoutes: RouteObject[] = [
  { path: 'sites', element: <SiteListPage /> },
  { path: 'sites/new', element: <SiteCreatePage /> },
  { path: 'sites/:id', element: <SiteDetailPage /> },
  { path: 'sites/:id/edit', element: <SiteEditPage /> },
];
