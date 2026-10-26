import type { RouteObject } from 'react-router-dom';
import { RoomCreatePage, RoomEditPage } from './RoomEditPages';
import { RoomDetailPage } from './RoomDetailPage';
import { RoomListPage } from './RoomListPage';

export const roomRoutes: RouteObject[] = [
  { path: 'rooms', element: <RoomListPage /> },
  { path: 'rooms/new', element: <RoomCreatePage /> },
  { path: 'rooms/:id', element: <RoomDetailPage /> },
  { path: 'rooms/:id/edit', element: <RoomEditPage /> },
];
