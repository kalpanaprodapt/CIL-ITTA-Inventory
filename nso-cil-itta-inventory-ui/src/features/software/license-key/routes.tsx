import type { RouteObject } from 'react-router-dom';
import { LicenseKeyCreatePage, LicenseKeyEditPage } from './LicenseKeyEditPages';
import { LicenseKeyDetailPage } from './LicenseKeyDetailPage';
import { LicenseKeyListPage } from './LicenseKeyListPage';

export const licenseKeyRoutes: RouteObject[] = [
  { path: 'license-keies', element: <LicenseKeyListPage /> },
  { path: 'license-keies/new', element: <LicenseKeyCreatePage /> },
  { path: 'license-keies/:id', element: <LicenseKeyDetailPage /> },
  { path: 'license-keies/:id/edit', element: <LicenseKeyEditPage /> },
];
