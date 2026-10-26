import type { RouteObject } from 'react-router-dom';
import { InvoiceCreatePage, InvoiceEditPage } from './InvoiceEditPages';
import { InvoiceDetailPage } from './InvoiceDetailPage';
import { InvoiceListPage } from './InvoiceListPage';

export const invoiceRoutes: RouteObject[] = [
  { path: 'invoices', element: <InvoiceListPage /> },
  { path: 'invoices/new', element: <InvoiceCreatePage /> },
  { path: 'invoices/:id', element: <InvoiceDetailPage /> },
  { path: 'invoices/:id/edit', element: <InvoiceEditPage /> },
];
