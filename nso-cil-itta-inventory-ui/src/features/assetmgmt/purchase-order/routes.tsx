import type { RouteObject } from 'react-router-dom';
import { PurchaseOrderCreatePage, PurchaseOrderEditPage } from './PurchaseOrderEditPages';
import { PurchaseOrderDetailPage } from './PurchaseOrderDetailPage';
import { PurchaseOrderListPage } from './PurchaseOrderListPage';

export const purchaseOrderRoutes: RouteObject[] = [
  { path: 'purchase-orders', element: <PurchaseOrderListPage /> },
  { path: 'purchase-orders/new', element: <PurchaseOrderCreatePage /> },
  { path: 'purchase-orders/:id', element: <PurchaseOrderDetailPage /> },
  { path: 'purchase-orders/:id/edit', element: <PurchaseOrderEditPage /> },
];
