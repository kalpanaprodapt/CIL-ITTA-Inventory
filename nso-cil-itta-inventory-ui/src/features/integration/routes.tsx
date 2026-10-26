import type { RouteObject } from 'react-router-dom';
import { integrationEndpointRoutes } from './integration-endpoint/routes';
import { syncJobRoutes } from './sync-job/routes';
import { syncMappingRoutes } from './sync-mapping/routes';
import { importBatchRoutes } from './import-batch/routes';
import { webhookSubscriptionRoutes } from './webhook-subscription/routes';

export const integrationDomainRoutes: RouteObject[] = [
  ...integrationEndpointRoutes,
  ...syncJobRoutes,
  ...syncMappingRoutes,
  ...importBatchRoutes,
  ...webhookSubscriptionRoutes,
].map((route) => ({ ...route, path: `integration/${route.path}` }));
