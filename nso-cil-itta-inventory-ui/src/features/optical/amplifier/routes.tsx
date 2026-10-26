import type { RouteObject } from 'react-router-dom';
import { AmplifierCreatePage, AmplifierEditPage } from './AmplifierEditPages';
import { AmplifierDetailPage } from './AmplifierDetailPage';
import { AmplifierListPage } from './AmplifierListPage';

export const amplifierRoutes: RouteObject[] = [
  { path: 'amplifiers', element: <AmplifierListPage /> },
  { path: 'amplifiers/new', element: <AmplifierCreatePage /> },
  { path: 'amplifiers/:id', element: <AmplifierDetailPage /> },
  { path: 'amplifiers/:id/edit', element: <AmplifierEditPage /> },
];
