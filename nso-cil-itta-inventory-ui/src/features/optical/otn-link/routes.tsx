import type { RouteObject } from 'react-router-dom';
import { OtnLinkCreatePage, OtnLinkEditPage } from './OtnLinkEditPages';
import { OtnLinkDetailPage } from './OtnLinkDetailPage';
import { OtnLinkListPage } from './OtnLinkListPage';

export const otnLinkRoutes: RouteObject[] = [
  { path: 'otn-links', element: <OtnLinkListPage /> },
  { path: 'otn-links/new', element: <OtnLinkCreatePage /> },
  { path: 'otn-links/:id', element: <OtnLinkDetailPage /> },
  { path: 'otn-links/:id/edit', element: <OtnLinkEditPage /> },
];
