import type { RouteObject } from 'react-router-dom';
import { OspfAreaCreatePage, OspfAreaEditPage } from './OspfAreaEditPages';
import { OspfAreaDetailPage } from './OspfAreaDetailPage';
import { OspfAreaListPage } from './OspfAreaListPage';

export const ospfAreaRoutes: RouteObject[] = [
  { path: 'ospf-areas', element: <OspfAreaListPage /> },
  { path: 'ospf-areas/new', element: <OspfAreaCreatePage /> },
  { path: 'ospf-areas/:id', element: <OspfAreaDetailPage /> },
  { path: 'ospf-areas/:id/edit', element: <OspfAreaEditPage /> },
];
