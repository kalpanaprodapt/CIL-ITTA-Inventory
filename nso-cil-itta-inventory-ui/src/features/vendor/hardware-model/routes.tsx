import type { RouteObject } from 'react-router-dom';
import { HardwareModelCreatePage, HardwareModelEditPage } from './HardwareModelEditPages';
import { HardwareModelDetailPage } from './HardwareModelDetailPage';
import { HardwareModelListPage } from './HardwareModelListPage';

export const hardwareModelRoutes: RouteObject[] = [
  { path: 'hardware-models', element: <HardwareModelListPage /> },
  { path: 'hardware-models/new', element: <HardwareModelCreatePage /> },
  { path: 'hardware-models/:id', element: <HardwareModelDetailPage /> },
  { path: 'hardware-models/:id/edit', element: <HardwareModelEditPage /> },
];
