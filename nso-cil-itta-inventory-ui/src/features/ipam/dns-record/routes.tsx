import type { RouteObject } from 'react-router-dom';
import { DnsRecordCreatePage, DnsRecordEditPage } from './DnsRecordEditPages';
import { DnsRecordDetailPage } from './DnsRecordDetailPage';
import { DnsRecordListPage } from './DnsRecordListPage';

export const dnsRecordRoutes: RouteObject[] = [
  { path: 'dns-records', element: <DnsRecordListPage /> },
  { path: 'dns-records/new', element: <DnsRecordCreatePage /> },
  { path: 'dns-records/:id', element: <DnsRecordDetailPage /> },
  { path: 'dns-records/:id/edit', element: <DnsRecordEditPage /> },
];
