import type { RouteObject } from 'react-router-dom';
import { EventSourceCreatePage, EventSourceEditPage } from './EventSourceEditPages';
import { EventSourceDetailPage } from './EventSourceDetailPage';
import { EventSourceListPage } from './EventSourceListPage';

export const eventSourceRoutes: RouteObject[] = [
  { path: 'event-sources', element: <EventSourceListPage /> },
  { path: 'event-sources/new', element: <EventSourceCreatePage /> },
  { path: 'event-sources/:id', element: <EventSourceDetailPage /> },
  { path: 'event-sources/:id/edit', element: <EventSourceEditPage /> },
];
