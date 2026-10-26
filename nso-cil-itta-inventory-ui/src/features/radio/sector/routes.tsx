import type { RouteObject } from 'react-router-dom';
import { SectorCreatePage, SectorEditPage } from './SectorEditPages';
import { SectorDetailPage } from './SectorDetailPage';
import { SectorListPage } from './SectorListPage';

export const sectorRoutes: RouteObject[] = [
  { path: 'sectors', element: <SectorListPage /> },
  { path: 'sectors/new', element: <SectorCreatePage /> },
  { path: 'sectors/:id', element: <SectorDetailPage /> },
  { path: 'sectors/:id/edit', element: <SectorEditPage /> },
];
