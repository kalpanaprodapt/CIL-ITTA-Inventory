import { createBrowserRouter } from 'react-router-dom';
import { AppShell } from '@/app/AppShell';
import { DashboardPage } from '@/app/DashboardPage';
import { NotFoundPage } from '@/app/NotFoundPage';
import { geographyDomainRoutes } from '@/features/geography/routes';
import { facilityDomainRoutes } from '@/features/facility/routes';
import { deviceDomainRoutes } from '@/features/device/routes';
import { portDomainRoutes } from '@/features/port/routes';
import { cablingDomainRoutes } from '@/features/cabling/routes';
import { vendorDomainRoutes } from '@/features/vendor/routes';
import { softwareDomainRoutes } from '@/features/software/routes';
import { ipamDomainRoutes } from '@/features/ipam/routes';
import { switchingDomainRoutes } from '@/features/switching/routes';
import { circuitDomainRoutes } from '@/features/circuit/routes';
import { topologyDomainRoutes } from '@/features/topology/routes';
import { customerDomainRoutes } from '@/features/customer/routes';
import { serviceDomainRoutes } from '@/features/service/routes';
import { mplsDomainRoutes } from '@/features/mpls/routes';
import { opticalDomainRoutes } from '@/features/optical/routes';
import { radioDomainRoutes } from '@/features/radio/routes';
import { virtualDomainRoutes } from '@/features/virtual/routes';
import { discoveryDomainRoutes } from '@/features/discovery/routes';
import { changeDomainRoutes } from '@/features/change/routes';
import { assetmgmtDomainRoutes } from '@/features/assetmgmt/routes';
import { complianceDomainRoutes } from '@/features/compliance/routes';
import { alarmDomainRoutes } from '@/features/alarm/routes';
import { capacityDomainRoutes } from '@/features/capacity/routes';
import { integrationDomainRoutes } from '@/features/integration/routes';

export const router = createBrowserRouter([
  {
    path: '/',
    element: <AppShell />,
    errorElement: <NotFoundPage />,
    children: [
      { index: true, element: <DashboardPage /> },
      ...geographyDomainRoutes,
      ...facilityDomainRoutes,
      ...deviceDomainRoutes,
      ...portDomainRoutes,
      ...cablingDomainRoutes,
      ...vendorDomainRoutes,
      ...softwareDomainRoutes,
      ...ipamDomainRoutes,
      ...switchingDomainRoutes,
      ...circuitDomainRoutes,
      ...topologyDomainRoutes,
      ...customerDomainRoutes,
      ...serviceDomainRoutes,
      ...mplsDomainRoutes,
      ...opticalDomainRoutes,
      ...radioDomainRoutes,
      ...virtualDomainRoutes,
      ...discoveryDomainRoutes,
      ...changeDomainRoutes,
      ...assetmgmtDomainRoutes,
      ...complianceDomainRoutes,
      ...alarmDomainRoutes,
      ...capacityDomainRoutes,
      ...integrationDomainRoutes,
      { path: '*', element: <NotFoundPage /> },
    ],
  },
]);
