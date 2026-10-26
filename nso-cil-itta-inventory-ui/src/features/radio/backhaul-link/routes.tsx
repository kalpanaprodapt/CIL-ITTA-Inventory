import type { RouteObject } from 'react-router-dom';
import { BackhaulLinkCreatePage, BackhaulLinkEditPage } from './BackhaulLinkEditPages';
import { BackhaulLinkDetailPage } from './BackhaulLinkDetailPage';
import { BackhaulLinkListPage } from './BackhaulLinkListPage';

export const backhaulLinkRoutes: RouteObject[] = [
  { path: 'backhaul-links', element: <BackhaulLinkListPage /> },
  { path: 'backhaul-links/new', element: <BackhaulLinkCreatePage /> },
  { path: 'backhaul-links/:id', element: <BackhaulLinkDetailPage /> },
  { path: 'backhaul-links/:id/edit', element: <BackhaulLinkEditPage /> },
];
