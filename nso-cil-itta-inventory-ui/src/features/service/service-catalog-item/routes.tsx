import type { RouteObject } from 'react-router-dom';
import { ServiceCatalogItemCreatePage, ServiceCatalogItemEditPage } from './ServiceCatalogItemEditPages';
import { ServiceCatalogItemDetailPage } from './ServiceCatalogItemDetailPage';
import { ServiceCatalogItemListPage } from './ServiceCatalogItemListPage';

export const serviceCatalogItemRoutes: RouteObject[] = [
  { path: 'service-catalog-items', element: <ServiceCatalogItemListPage /> },
  { path: 'service-catalog-items/new', element: <ServiceCatalogItemCreatePage /> },
  { path: 'service-catalog-items/:id', element: <ServiceCatalogItemDetailPage /> },
  { path: 'service-catalog-items/:id/edit', element: <ServiceCatalogItemEditPage /> },
];
