import type { RouteObject } from 'react-router-dom';
import { vlanRoutes } from './vlan/routes';
import { vlanPoolRoutes } from './vlan-pool/routes';
import { routeTargetRoutes } from './route-target/routes';
import { bgpSessionRoutes } from './bgp-session/routes';
import { ospfAreaRoutes } from './ospf-area/routes';

export const switchingDomainRoutes: RouteObject[] = [
  ...vlanRoutes,
  ...vlanPoolRoutes,
  ...routeTargetRoutes,
  ...bgpSessionRoutes,
  ...ospfAreaRoutes,
].map((route) => ({ ...route, path: `switching/${route.path}` }));
