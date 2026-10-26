import type { RouteObject } from 'react-router-dom';
import { CloudAccountCreatePage, CloudAccountEditPage } from './CloudAccountEditPages';
import { CloudAccountDetailPage } from './CloudAccountDetailPage';
import { CloudAccountListPage } from './CloudAccountListPage';

export const cloudAccountRoutes: RouteObject[] = [
  { path: 'cloud-accounts', element: <CloudAccountListPage /> },
  { path: 'cloud-accounts/new', element: <CloudAccountCreatePage /> },
  { path: 'cloud-accounts/:id', element: <CloudAccountDetailPage /> },
  { path: 'cloud-accounts/:id/edit', element: <CloudAccountEditPage /> },
];
