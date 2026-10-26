import type { RouteObject } from 'react-router-dom';
import { ContractCreatePage, ContractEditPage } from './ContractEditPages';
import { ContractDetailPage } from './ContractDetailPage';
import { ContractListPage } from './ContractListPage';

export const contractRoutes: RouteObject[] = [
  { path: 'contracts', element: <ContractListPage /> },
  { path: 'contracts/new', element: <ContractCreatePage /> },
  { path: 'contracts/:id', element: <ContractDetailPage /> },
  { path: 'contracts/:id/edit', element: <ContractEditPage /> },
];
