import type { RouteObject } from 'react-router-dom';
import { TechnicianCreatePage, TechnicianEditPage } from './TechnicianEditPages';
import { TechnicianDetailPage } from './TechnicianDetailPage';
import { TechnicianListPage } from './TechnicianListPage';

export const technicianRoutes: RouteObject[] = [
  { path: 'technicians', element: <TechnicianListPage /> },
  { path: 'technicians/new', element: <TechnicianCreatePage /> },
  { path: 'technicians/:id', element: <TechnicianDetailPage /> },
  { path: 'technicians/:id/edit', element: <TechnicianEditPage /> },
];
