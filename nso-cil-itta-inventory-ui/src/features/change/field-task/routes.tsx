import type { RouteObject } from 'react-router-dom';
import { FieldTaskCreatePage, FieldTaskEditPage } from './FieldTaskEditPages';
import { FieldTaskDetailPage } from './FieldTaskDetailPage';
import { FieldTaskListPage } from './FieldTaskListPage';

export const fieldTaskRoutes: RouteObject[] = [
  { path: 'field-tasks', element: <FieldTaskListPage /> },
  { path: 'field-tasks/new', element: <FieldTaskCreatePage /> },
  { path: 'field-tasks/:id', element: <FieldTaskDetailPage /> },
  { path: 'field-tasks/:id/edit', element: <FieldTaskEditPage /> },
];
