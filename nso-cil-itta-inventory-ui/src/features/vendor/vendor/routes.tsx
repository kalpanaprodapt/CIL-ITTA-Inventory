import type { RouteObject } from 'react-router-dom';
import { VendorCreatePage, VendorEditPage } from './VendorEditPages';
import { VendorDetailPage } from './VendorDetailPage';
import { VendorListPage } from './VendorListPage';

export const vendorRoutes: RouteObject[] = [
  { path: 'vendors', element: <VendorListPage /> },
  { path: 'vendors/new', element: <VendorCreatePage /> },
  { path: 'vendors/:id', element: <VendorDetailPage /> },
  { path: 'vendors/:id/edit', element: <VendorEditPage /> },
];
