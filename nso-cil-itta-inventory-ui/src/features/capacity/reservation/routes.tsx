import type { RouteObject } from 'react-router-dom';
import { ReservationCreatePage, ReservationEditPage } from './ReservationEditPages';
import { ReservationDetailPage } from './ReservationDetailPage';
import { ReservationListPage } from './ReservationListPage';

export const reservationRoutes: RouteObject[] = [
  { path: 'reservations', element: <ReservationListPage /> },
  { path: 'reservations/new', element: <ReservationCreatePage /> },
  { path: 'reservations/:id', element: <ReservationDetailPage /> },
  { path: 'reservations/:id/edit', element: <ReservationEditPage /> },
];
