import type { RouteObject } from 'react-router-dom';
import { RoadmCreatePage, RoadmEditPage } from './RoadmEditPages';
import { RoadmDetailPage } from './RoadmDetailPage';
import { RoadmListPage } from './RoadmListPage';

export const roadmRoutes: RouteObject[] = [
  { path: 'roadms', element: <RoadmListPage /> },
  { path: 'roadms/new', element: <RoadmCreatePage /> },
  { path: 'roadms/:id', element: <RoadmDetailPage /> },
  { path: 'roadms/:id/edit', element: <RoadmEditPage /> },
];
