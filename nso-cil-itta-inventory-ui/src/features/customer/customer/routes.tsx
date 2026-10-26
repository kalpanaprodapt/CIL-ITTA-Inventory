import type { RouteObject } from 'react-router-dom';
import { CustomerCreatePage, CustomerEditPage } from './CustomerEditPages';
import { CustomerDetailPage } from './CustomerDetailPage';
import { CustomerListPage } from './CustomerListPage';

export const customerRoutes: RouteObject[] = [
  { path: 'customers', element: <CustomerListPage /> },
  { path: 'customers/new', element: <CustomerCreatePage /> },
  { path: 'customers/:id', element: <CustomerDetailPage /> },
  { path: 'customers/:id/edit', element: <CustomerEditPage /> },
];
