import type { RouteObject } from 'react-router-dom';
import { ipSubnetRoutes } from './ip-subnet/routes';
import { ipAddressRoutes } from './ip-address/routes';
import { ipPoolRoutes } from './ip-pool/routes';
import { vrfInstanceRoutes } from './vrf-instance/routes';
import { dnsRecordRoutes } from './dns-record/routes';

export const ipamDomainRoutes: RouteObject[] = [
  ...ipSubnetRoutes,
  ...ipAddressRoutes,
  ...ipPoolRoutes,
  ...vrfInstanceRoutes,
  ...dnsRecordRoutes,
].map((route) => ({ ...route, path: `ipam/${route.path}` }));
