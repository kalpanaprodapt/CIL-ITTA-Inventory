import type { RouteObject } from 'react-router-dom';
import { RadioUnitCreatePage, RadioUnitEditPage } from './RadioUnitEditPages';
import { RadioUnitDetailPage } from './RadioUnitDetailPage';
import { RadioUnitListPage } from './RadioUnitListPage';

export const radioUnitRoutes: RouteObject[] = [
  { path: 'radio-units', element: <RadioUnitListPage /> },
  { path: 'radio-units/new', element: <RadioUnitCreatePage /> },
  { path: 'radio-units/:id', element: <RadioUnitDetailPage /> },
  { path: 'radio-units/:id/edit', element: <RadioUnitEditPage /> },
];
