import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { DISCOVERY_PROFILE_PROTOCOL_LABELS } from './types';
import type { DiscoveryProfile } from './types';

export const discoveryProfileColumns: Column<DiscoveryProfile>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/discovery/discovery-profiles/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'profileName',
    header: 'Profile Name',
    sortable: true,
    align: 'left',
    render: (row) => row.profileName ?? '—',
  },
  {
    key: 'protocol',
    header: 'Protocol',
    sortable: true,
    align: 'left',
    render: (row) => row.protocol ? DISCOVERY_PROFILE_PROTOCOL_LABELS[row.protocol] : '—',
  },
  {
    key: 'credentialRef',
    header: 'Credential Ref',
    sortable: true,
    align: 'left',
    render: (row) => row.credentialRef ?? '—',
  },
  {
    key: 'scopeCidr',
    header: 'Scope Cidr',
    sortable: true,
    align: 'left',
    render: (row) => row.scopeCidr ?? '—',
  },
  {
    key: 'scheduleCron',
    header: 'Schedule Cron',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.scheduleCron ?? '—',
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
];

export const discoveryProfileDefaultVisible = discoveryProfileColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
