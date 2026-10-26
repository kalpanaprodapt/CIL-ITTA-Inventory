import type { RouteObject } from 'react-router-dom';
import { BaseStationCreatePage, BaseStationEditPage } from './BaseStationEditPages';
import { BaseStationDetailPage } from './BaseStationDetailPage';
import { BaseStationListPage } from './BaseStationListPage';

export const baseStationRoutes: RouteObject[] = [
  { path: 'base-stations', element: <BaseStationListPage /> },
  { path: 'base-stations/new', element: <BaseStationCreatePage /> },
  { path: 'base-stations/:id', element: <BaseStationDetailPage /> },
  { path: 'base-stations/:id/edit', element: <BaseStationEditPage /> },
];
