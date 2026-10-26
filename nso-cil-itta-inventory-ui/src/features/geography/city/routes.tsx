import type { RouteObject } from 'react-router-dom';
import { CityCreatePage, CityEditPage } from './CityEditPages';
import { CityDetailPage } from './CityDetailPage';
import { CityListPage } from './CityListPage';

export const cityRoutes: RouteObject[] = [
  { path: 'cities', element: <CityListPage /> },
  { path: 'cities/new', element: <CityCreatePage /> },
  { path: 'cities/:id', element: <CityDetailPage /> },
  { path: 'cities/:id/edit', element: <CityEditPage /> },
];
