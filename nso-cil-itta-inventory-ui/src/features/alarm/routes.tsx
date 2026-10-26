import type { RouteObject } from 'react-router-dom';
import { alarmDefinitionRoutes } from './alarm-definition/routes';
import { alarmInstanceRoutes } from './alarm-instance/routes';
import { eventSourceRoutes } from './event-source/routes';
import { suppressionRuleRoutes } from './suppression-rule/routes';
import { ticketLinkRoutes } from './ticket-link/routes';

export const alarmDomainRoutes: RouteObject[] = [
  ...alarmDefinitionRoutes,
  ...alarmInstanceRoutes,
  ...eventSourceRoutes,
  ...suppressionRuleRoutes,
  ...ticketLinkRoutes,
].map((route) => ({ ...route, path: `alarm/${route.path}` }));
