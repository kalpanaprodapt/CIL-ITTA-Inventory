import type { RouteObject } from 'react-router-dom';
import { AntennaCreatePage, AntennaEditPage } from './AntennaEditPages';
import { AntennaDetailPage } from './AntennaDetailPage';
import { AntennaListPage } from './AntennaListPage';

export const antennaRoutes: RouteObject[] = [
  { path: 'antennas', element: <AntennaListPage /> },
  { path: 'antennas/new', element: <AntennaCreatePage /> },
  { path: 'antennas/:id', element: <AntennaDetailPage /> },
  { path: 'antennas/:id/edit', element: <AntennaEditPage /> },
];
