import type { RouteObject } from 'react-router-dom';
import { LineCardCreatePage, LineCardEditPage } from './LineCardEditPages';
import { LineCardDetailPage } from './LineCardDetailPage';
import { LineCardListPage } from './LineCardListPage';

export const lineCardRoutes: RouteObject[] = [
  { path: 'line-cards', element: <LineCardListPage /> },
  { path: 'line-cards/new', element: <LineCardCreatePage /> },
  { path: 'line-cards/:id', element: <LineCardDetailPage /> },
  { path: 'line-cards/:id/edit', element: <LineCardEditPage /> },
];
