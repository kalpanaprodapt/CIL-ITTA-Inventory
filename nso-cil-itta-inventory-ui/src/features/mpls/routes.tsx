import type { RouteObject } from 'react-router-dom';
import { mplsLspRoutes } from './mpls-lsp/routes';
import { vpnServiceRoutes } from './vpn-service/routes';
import { pseudoWireRoutes } from './pseudo-wire/routes';
import { tunnelEndpointRoutes } from './tunnel-endpoint/routes';
import { qosPolicyRoutes } from './qos-policy/routes';

export const mplsDomainRoutes: RouteObject[] = [
  ...mplsLspRoutes,
  ...vpnServiceRoutes,
  ...pseudoWireRoutes,
  ...tunnelEndpointRoutes,
  ...qosPolicyRoutes,
].map((route) => ({ ...route, path: `mpls/${route.path}` }));
