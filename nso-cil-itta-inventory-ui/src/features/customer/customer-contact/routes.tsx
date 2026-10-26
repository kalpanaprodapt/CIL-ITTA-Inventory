import type { RouteObject } from 'react-router-dom';
import { CustomerContactCreatePage, CustomerContactEditPage } from './CustomerContactEditPages';
import { CustomerContactDetailPage } from './CustomerContactDetailPage';
import { CustomerContactListPage } from './CustomerContactListPage';

export const customerContactRoutes: RouteObject[] = [
  { path: 'customer-contacts', element: <CustomerContactListPage /> },
  { path: 'customer-contacts/new', element: <CustomerContactCreatePage /> },
  { path: 'customer-contacts/:id', element: <CustomerContactDetailPage /> },
  { path: 'customer-contacts/:id/edit', element: <CustomerContactEditPage /> },
];
