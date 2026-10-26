import type { RouteObject } from 'react-router-dom';
import { SoftwareImageCreatePage, SoftwareImageEditPage } from './SoftwareImageEditPages';
import { SoftwareImageDetailPage } from './SoftwareImageDetailPage';
import { SoftwareImageListPage } from './SoftwareImageListPage';

export const softwareImageRoutes: RouteObject[] = [
  { path: 'software-images', element: <SoftwareImageListPage /> },
  { path: 'software-images/new', element: <SoftwareImageCreatePage /> },
  { path: 'software-images/:id', element: <SoftwareImageDetailPage /> },
  { path: 'software-images/:id/edit', element: <SoftwareImageEditPage /> },
];
