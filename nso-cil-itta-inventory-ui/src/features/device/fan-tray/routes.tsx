import type { RouteObject } from 'react-router-dom';
import { FanTrayCreatePage, FanTrayEditPage } from './FanTrayEditPages';
import { FanTrayDetailPage } from './FanTrayDetailPage';
import { FanTrayListPage } from './FanTrayListPage';

export const fanTrayRoutes: RouteObject[] = [
  { path: 'fan-traies', element: <FanTrayListPage /> },
  { path: 'fan-traies/new', element: <FanTrayCreatePage /> },
  { path: 'fan-traies/:id', element: <FanTrayDetailPage /> },
  { path: 'fan-traies/:id/edit', element: <FanTrayEditPage /> },
];
