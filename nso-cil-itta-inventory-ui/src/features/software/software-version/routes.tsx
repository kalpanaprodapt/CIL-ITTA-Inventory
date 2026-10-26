import type { RouteObject } from 'react-router-dom';
import { SoftwareVersionCreatePage, SoftwareVersionEditPage } from './SoftwareVersionEditPages';
import { SoftwareVersionDetailPage } from './SoftwareVersionDetailPage';
import { SoftwareVersionListPage } from './SoftwareVersionListPage';

export const softwareVersionRoutes: RouteObject[] = [
  { path: 'software-versions', element: <SoftwareVersionListPage /> },
  { path: 'software-versions/new', element: <SoftwareVersionCreatePage /> },
  { path: 'software-versions/:id', element: <SoftwareVersionDetailPage /> },
  { path: 'software-versions/:id/edit', element: <SoftwareVersionEditPage /> },
];
