import type { RouteObject } from 'react-router-dom';
import { AuditRecordCreatePage, AuditRecordEditPage } from './AuditRecordEditPages';
import { AuditRecordDetailPage } from './AuditRecordDetailPage';
import { AuditRecordListPage } from './AuditRecordListPage';

export const auditRecordRoutes: RouteObject[] = [
  { path: 'audit-records', element: <AuditRecordListPage /> },
  { path: 'audit-records/new', element: <AuditRecordCreatePage /> },
  { path: 'audit-records/:id', element: <AuditRecordDetailPage /> },
  { path: 'audit-records/:id/edit', element: <AuditRecordEditPage /> },
];
