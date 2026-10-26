import type { RouteObject } from 'react-router-dom';
import { CountryCreatePage, CountryEditPage } from './CountryEditPages';
import { CountryDetailPage } from './CountryDetailPage';
import { CountryListPage } from './CountryListPage';

export const countryRoutes: RouteObject[] = [
  { path: 'countries', element: <CountryListPage /> },
  { path: 'countries/new', element: <CountryCreatePage /> },
  { path: 'countries/:id', element: <CountryDetailPage /> },
  { path: 'countries/:id/edit', element: <CountryEditPage /> },
];
