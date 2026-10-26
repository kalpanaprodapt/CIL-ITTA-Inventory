import type { RouteObject } from 'react-router-dom';
import { TicketLinkCreatePage, TicketLinkEditPage } from './TicketLinkEditPages';
import { TicketLinkDetailPage } from './TicketLinkDetailPage';
import { TicketLinkListPage } from './TicketLinkListPage';

export const ticketLinkRoutes: RouteObject[] = [
  { path: 'ticket-links', element: <TicketLinkListPage /> },
  { path: 'ticket-links/new', element: <TicketLinkCreatePage /> },
  { path: 'ticket-links/:id', element: <TicketLinkDetailPage /> },
  { path: 'ticket-links/:id/edit', element: <TicketLinkEditPage /> },
];
