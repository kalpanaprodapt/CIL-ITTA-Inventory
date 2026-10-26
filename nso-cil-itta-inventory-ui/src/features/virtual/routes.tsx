import type { RouteObject } from 'react-router-dom';
import { vnfRoutes } from './vnf/routes';
import { hypervisorRoutes } from './hypervisor/routes';
import { virtualMachineRoutes } from './virtual-machine/routes';
import { cloudAccountRoutes } from './cloud-account/routes';
import { k8sClusterRoutes } from './k8s-cluster/routes';

export const virtualDomainRoutes: RouteObject[] = [
  ...vnfRoutes,
  ...hypervisorRoutes,
  ...virtualMachineRoutes,
  ...cloudAccountRoutes,
  ...k8sClusterRoutes,
].map((route) => ({ ...route, path: `virtual/${route.path}` }));
