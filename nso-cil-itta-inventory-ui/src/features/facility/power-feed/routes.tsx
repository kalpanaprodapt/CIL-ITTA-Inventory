import type { RouteObject } from 'react-router-dom';
import { PowerFeedCreatePage, PowerFeedEditPage } from './PowerFeedEditPages';
import { PowerFeedDetailPage } from './PowerFeedDetailPage';
import { PowerFeedListPage } from './PowerFeedListPage';

export const powerFeedRoutes: RouteObject[] = [
  { path: 'power-feeds', element: <PowerFeedListPage /> },
  { path: 'power-feeds/new', element: <PowerFeedCreatePage /> },
  { path: 'power-feeds/:id', element: <PowerFeedDetailPage /> },
  { path: 'power-feeds/:id/edit', element: <PowerFeedEditPage /> },
];
