import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { SLA_PROFILE_CRITICALITY_LABELS } from './types';
import type { SlaProfile } from './types';

export const slaProfileColumns: Column<SlaProfile>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/customer/sla-profiles/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'availabilityPct',
    header: 'Availability Pct',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.availabilityPct),
  },
  {
    key: 'mttrMinutes',
    header: 'Mttr Minutes',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.mttrMinutes),
  },
  {
    key: 'latencyMs',
    header: 'Latency Ms',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.latencyMs),
  },
  {
    key: 'jitterMs',
    header: 'Jitter Ms',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatDecimal(row.jitterMs),
  },
  {
    key: 'creditPolicy',
    header: 'Credit Policy',
    sortable: false,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => truncate(row.creditPolicy, 60),
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
    render: (row) => row.criticality ? SLA_PROFILE_CRITICALITY_LABELS[row.criticality] : '—',
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

export const slaProfileDefaultVisible = slaProfileColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
