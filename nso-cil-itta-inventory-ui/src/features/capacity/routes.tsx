import type { RouteObject } from 'react-router-dom';
import { capacityPlanRoutes } from './capacity-plan/routes';
import { utilizationSampleRoutes } from './utilization-sample/routes';
import { thresholdPolicyRoutes } from './threshold-policy/routes';
import { forecastModelRoutes } from './forecast-model/routes';
import { reservationRoutes } from './reservation/routes';

export const capacityDomainRoutes: RouteObject[] = [
  ...capacityPlanRoutes,
  ...utilizationSampleRoutes,
  ...thresholdPolicyRoutes,
  ...forecastModelRoutes,
  ...reservationRoutes,
].map((route) => ({ ...route, path: `capacity/${route.path}` }));
