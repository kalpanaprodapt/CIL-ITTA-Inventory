import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { SYNC_JOB_DIRECTION_LABELS, SYNC_JOB_LAST_STATUS_LABELS, SYNC_JOB_CRITICALITY_LABELS } from './types';
import type { SyncJob } from './types';

export const syncJobColumns: Column<SyncJob>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/integration/sync-jobs/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'endpointId',
    header: 'Endpoint Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.endpointId} />,
  },
  {
    key: 'direction',
    header: 'Direction',
    sortable: true,
    align: 'left',
    render: (row) => row.direction ? SYNC_JOB_DIRECTION_LABELS[row.direction] : '—',
  },
  {
    key: 'scheduleCron',
    header: 'Schedule Cron',
    sortable: true,
    align: 'left',
    render: (row) => row.scheduleCron ?? '—',
  },
  {
    key: 'lastRunAt',
    header: 'Last Run At',
    sortable: true,
    align: 'left',
    render: (row) => formatDateTime(row.lastRunAt),
  },
  {
    key: 'lastStatus',
    header: 'Last Status',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.lastStatus ? SYNC_JOB_LAST_STATUS_LABELS[row.lastStatus] : '—',
  },
  {
    key: 'owner',
    header: 'Owner',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.owner ?? '—',
  },
  {
    key: 'criticality',
    header: 'Criticality',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.criticality ? SYNC_JOB_CRITICALITY_LABELS[row.criticality] : '—',
  },
  {
    key: 'costCenter',
    header: 'Cost Center',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.costCenter ?? '—',
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
];

export const syncJobDefaultVisible = syncJobColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
