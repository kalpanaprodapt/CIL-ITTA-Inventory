import type { RouteObject } from 'react-router-dom';
import { WdmSystemCreatePage, WdmSystemEditPage } from './WdmSystemEditPages';
import { WdmSystemDetailPage } from './WdmSystemDetailPage';
import { WdmSystemListPage } from './WdmSystemListPage';

export const wdmSystemRoutes: RouteObject[] = [
  { path: 'wdm-systems', element: <WdmSystemListPage /> },
  { path: 'wdm-systems/new', element: <WdmSystemCreatePage /> },
  { path: 'wdm-systems/:id', element: <WdmSystemDetailPage /> },
  { path: 'wdm-systems/:id/edit', element: <WdmSystemEditPage /> },
];
