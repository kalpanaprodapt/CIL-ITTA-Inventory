import type { RouteObject } from 'react-router-dom';
import { ThresholdPolicyCreatePage, ThresholdPolicyEditPage } from './ThresholdPolicyEditPages';
import { ThresholdPolicyDetailPage } from './ThresholdPolicyDetailPage';
import { ThresholdPolicyListPage } from './ThresholdPolicyListPage';

export const thresholdPolicyRoutes: RouteObject[] = [
  { path: 'threshold-policies', element: <ThresholdPolicyListPage /> },
  { path: 'threshold-policies/new', element: <ThresholdPolicyCreatePage /> },
  { path: 'threshold-policies/:id', element: <ThresholdPolicyDetailPage /> },
  { path: 'threshold-policies/:id/edit', element: <ThresholdPolicyEditPage /> },
];
