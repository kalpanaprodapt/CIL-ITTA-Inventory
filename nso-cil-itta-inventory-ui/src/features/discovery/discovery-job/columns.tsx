import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { DISCOVERY_JOB_JOB_STATE_LABELS, DISCOVERY_JOB_CRITICALITY_LABELS } from './types';
import type { DiscoveryJob } from './types';

export const discoveryJobColumns: Column<DiscoveryJob>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/discovery/discovery-jobs/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'profileId',
    header: 'Profile Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.profileId} />,
  },
  {
    key: 'startedAt',
    header: 'Started At',
    sortable: true,
    align: 'left',
    render: (row) => formatDateTime(row.startedAt),
  },
  {
    key: 'finishedAt',
    header: 'Finished At',
    sortable: true,
    align: 'left',
    render: (row) => formatDateTime(row.finishedAt),
  },
  {
    key: 'jobState',
    header: 'Job State',
    sortable: true,
    align: 'left',
    render: (row) => row.jobState ? DISCOVERY_JOB_JOB_STATE_LABELS[row.jobState] : '—',
  },
  {
    key: 'assetsFound',
    header: 'Assets Found',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatNumber(row.assetsFound),
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
    render: (row) => row.criticality ? DISCOVERY_JOB_CRITICALITY_LABELS[row.criticality] : '—',
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

export const discoveryJobDefaultVisible = discoveryJobColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
