import type { RouteObject } from 'react-router-dom';
import { PowerSupplyCreatePage, PowerSupplyEditPage } from './PowerSupplyEditPages';
import { PowerSupplyDetailPage } from './PowerSupplyDetailPage';
import { PowerSupplyListPage } from './PowerSupplyListPage';

export const powerSupplyRoutes: RouteObject[] = [
  { path: 'power-supplies', element: <PowerSupplyListPage /> },
  { path: 'power-supplies/new', element: <PowerSupplyCreatePage /> },
  { path: 'power-supplies/:id', element: <PowerSupplyDetailPage /> },
  { path: 'power-supplies/:id/edit', element: <PowerSupplyEditPage /> },
];
