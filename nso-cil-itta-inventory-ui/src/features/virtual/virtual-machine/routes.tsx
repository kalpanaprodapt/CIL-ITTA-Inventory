import type { RouteObject } from 'react-router-dom';
import { VirtualMachineCreatePage, VirtualMachineEditPage } from './VirtualMachineEditPages';
import { VirtualMachineDetailPage } from './VirtualMachineDetailPage';
import { VirtualMachineListPage } from './VirtualMachineListPage';

export const virtualMachineRoutes: RouteObject[] = [
  { path: 'virtual-machines', element: <VirtualMachineListPage /> },
  { path: 'virtual-machines/new', element: <VirtualMachineCreatePage /> },
  { path: 'virtual-machines/:id', element: <VirtualMachineDetailPage /> },
  { path: 'virtual-machines/:id/edit', element: <VirtualMachineEditPage /> },
];
