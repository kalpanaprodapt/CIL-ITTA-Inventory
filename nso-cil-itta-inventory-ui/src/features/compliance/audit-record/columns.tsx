import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { AUDIT_RECORD_LIFECYCLE_STAGE_LABELS } from './types';
import type { AuditRecord } from './types';

export const auditRecordColumns: Column<AuditRecord>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/compliance/audit-records/${row.id}`}>{row.code}</RouterLink>,
  },
  {
    key: 'name',
    header: 'Name',
    sortable: true,
    align: 'left',
    render: (row) => row.name ?? '—',
  },
  {
    key: 'description',
    header: 'Description',
    sortable: false,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => truncate(row.description, 60),
  },
  {
    key: 'status',
    header: 'Status',
    sortable: true,
    align: 'left',
    render: (row) => <StatusBadge status={row.status} />,
  },
  {
    key: 'externalRef',
    header: 'External Ref',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.externalRef ?? '—',
  },
  {
    key: 'actor',
    header: 'Actor',
    sortable: true,
    align: 'left',
    render: (row) => row.actor ?? '—',
  },
  {
    key: 'action',
    header: 'Action',
    sortable: true,
    align: 'left',
    render: (row) => row.action ?? '—',
  },
  {
    key: 'entityRef',
    header: 'Entity Ref',
    sortable: true,
    align: 'left',
    render: (row) => row.entityRef ?? '—',
  },
  {
    key: 'occurredAt',
    header: 'Occurred At',
    sortable: true,
    align: 'left',
    render: (row) => formatDateTime(row.occurredAt),
  },
  {
    key: 'beforeState',
    header: 'Before State',
    sortable: false,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => truncate(row.beforeState, 60),
  },
  {
    key: 'afterState',
    header: 'After State',
    sortable: false,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => truncate(row.afterState, 60),
  },
  {
    key: 'notes',
    header: 'Notes',
    sortable: false,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => truncate(row.notes, 60),
  },
  {
    key: 'effectiveFrom',
    header: 'Effective From',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => formatDate(row.effectiveFrom),
  },
  {
    key: 'effectiveTo',
    header: 'Effective To',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => formatDate(row.effectiveTo),
  },
  {
    key: 'labels',
    header: 'Labels',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.labels ?? '—',
  },
  {
    key: 'lifecycleStage',
    header: 'Lifecycle Stage',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.lifecycleStage ? AUDIT_RECORD_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
  },
];

export const auditRecordDefaultVisible = auditRecordColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
