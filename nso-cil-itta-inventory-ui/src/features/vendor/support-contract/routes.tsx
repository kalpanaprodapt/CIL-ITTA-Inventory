import type { RouteObject } from 'react-router-dom';
import { SupportContractCreatePage, SupportContractEditPage } from './SupportContractEditPages';
import { SupportContractDetailPage } from './SupportContractDetailPage';
import { SupportContractListPage } from './SupportContractListPage';

export const supportContractRoutes: RouteObject[] = [
  { path: 'support-contracts', element: <SupportContractListPage /> },
  { path: 'support-contracts/new', element: <SupportContractCreatePage /> },
  { path: 'support-contracts/:id', element: <SupportContractDetailPage /> },
  { path: 'support-contracts/:id/edit', element: <SupportContractEditPage /> },
];
