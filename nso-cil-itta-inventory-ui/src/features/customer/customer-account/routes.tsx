import type { RouteObject } from 'react-router-dom';
import { CustomerAccountCreatePage, CustomerAccountEditPage } from './CustomerAccountEditPages';
import { CustomerAccountDetailPage } from './CustomerAccountDetailPage';
import { CustomerAccountListPage } from './CustomerAccountListPage';

export const customerAccountRoutes: RouteObject[] = [
  { path: 'customer-accounts', element: <CustomerAccountListPage /> },
  { path: 'customer-accounts/new', element: <CustomerAccountCreatePage /> },
  { path: 'customer-accounts/:id', element: <CustomerAccountDetailPage /> },
  { path: 'customer-accounts/:id/edit', element: <CustomerAccountEditPage /> },
];
