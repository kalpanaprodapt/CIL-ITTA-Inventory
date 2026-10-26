import type { RouteObject } from 'react-router-dom';
import { QosPolicyCreatePage, QosPolicyEditPage } from './QosPolicyEditPages';
import { QosPolicyDetailPage } from './QosPolicyDetailPage';
import { QosPolicyListPage } from './QosPolicyListPage';

export const qosPolicyRoutes: RouteObject[] = [
  { path: 'qos-policies', element: <QosPolicyListPage /> },
  { path: 'qos-policies/new', element: <QosPolicyCreatePage /> },
  { path: 'qos-policies/:id', element: <QosPolicyDetailPage /> },
  { path: 'qos-policies/:id/edit', element: <QosPolicyEditPage /> },
];
