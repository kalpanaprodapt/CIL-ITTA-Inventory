import type { RouteObject } from 'react-router-dom';
import { AlarmDefinitionCreatePage, AlarmDefinitionEditPage } from './AlarmDefinitionEditPages';
import { AlarmDefinitionDetailPage } from './AlarmDefinitionDetailPage';
import { AlarmDefinitionListPage } from './AlarmDefinitionListPage';

export const alarmDefinitionRoutes: RouteObject[] = [
  { path: 'alarm-definitions', element: <AlarmDefinitionListPage /> },
  { path: 'alarm-definitions/new', element: <AlarmDefinitionCreatePage /> },
  { path: 'alarm-definitions/:id', element: <AlarmDefinitionDetailPage /> },
  { path: 'alarm-definitions/:id/edit', element: <AlarmDefinitionEditPage /> },
];
