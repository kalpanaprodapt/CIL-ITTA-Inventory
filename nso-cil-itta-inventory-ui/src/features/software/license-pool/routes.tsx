import type { RouteObject } from 'react-router-dom';
import { LicensePoolCreatePage, LicensePoolEditPage } from './LicensePoolEditPages';
import { LicensePoolDetailPage } from './LicensePoolDetailPage';
import { LicensePoolListPage } from './LicensePoolListPage';

export const licensePoolRoutes: RouteObject[] = [
  { path: 'license-pools', element: <LicensePoolListPage /> },
  { path: 'license-pools/new', element: <LicensePoolCreatePage /> },
  { path: 'license-pools/:id', element: <LicensePoolDetailPage /> },
  { path: 'license-pools/:id/edit', element: <LicensePoolEditPage /> },
];
