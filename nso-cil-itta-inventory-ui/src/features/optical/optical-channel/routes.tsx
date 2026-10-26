import type { RouteObject } from 'react-router-dom';
import { OpticalChannelCreatePage, OpticalChannelEditPage } from './OpticalChannelEditPages';
import { OpticalChannelDetailPage } from './OpticalChannelDetailPage';
import { OpticalChannelListPage } from './OpticalChannelListPage';

export const opticalChannelRoutes: RouteObject[] = [
  { path: 'optical-channels', element: <OpticalChannelListPage /> },
  { path: 'optical-channels/new', element: <OpticalChannelCreatePage /> },
  { path: 'optical-channels/:id', element: <OpticalChannelDetailPage /> },
  { path: 'optical-channels/:id/edit', element: <OpticalChannelEditPage /> },
];
