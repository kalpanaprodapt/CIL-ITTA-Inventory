import type { RouteObject } from 'react-router-dom';
import { TransceiverCreatePage, TransceiverEditPage } from './TransceiverEditPages';
import { TransceiverDetailPage } from './TransceiverDetailPage';
import { TransceiverListPage } from './TransceiverListPage';

export const transceiverRoutes: RouteObject[] = [
  { path: 'transceivers', element: <TransceiverListPage /> },
  { path: 'transceivers/new', element: <TransceiverCreatePage /> },
  { path: 'transceivers/:id', element: <TransceiverDetailPage /> },
  { path: 'transceivers/:id/edit', element: <TransceiverEditPage /> },
];
