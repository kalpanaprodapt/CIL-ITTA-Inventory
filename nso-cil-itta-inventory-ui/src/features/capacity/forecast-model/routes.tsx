import type { RouteObject } from 'react-router-dom';
import { ForecastModelCreatePage, ForecastModelEditPage } from './ForecastModelEditPages';
import { ForecastModelDetailPage } from './ForecastModelDetailPage';
import { ForecastModelListPage } from './ForecastModelListPage';

export const forecastModelRoutes: RouteObject[] = [
  { path: 'forecast-models', element: <ForecastModelListPage /> },
  { path: 'forecast-models/new', element: <ForecastModelCreatePage /> },
  { path: 'forecast-models/:id', element: <ForecastModelDetailPage /> },
  { path: 'forecast-models/:id/edit', element: <ForecastModelEditPage /> },
];
