import type { RouteObject } from 'react-router-dom';
import { PseudoWireCreatePage, PseudoWireEditPage } from './PseudoWireEditPages';
import { PseudoWireDetailPage } from './PseudoWireDetailPage';
import { PseudoWireListPage } from './PseudoWireListPage';

export const pseudoWireRoutes: RouteObject[] = [
  { path: 'pseudo-wires', element: <PseudoWireListPage /> },
  { path: 'pseudo-wires/new', element: <PseudoWireCreatePage /> },
  { path: 'pseudo-wires/:id', element: <PseudoWireDetailPage /> },
  { path: 'pseudo-wires/:id/edit', element: <PseudoWireEditPage /> },
];
