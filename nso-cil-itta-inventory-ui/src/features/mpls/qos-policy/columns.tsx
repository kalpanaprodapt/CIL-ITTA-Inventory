import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { QOS_POLICY_LIFECYCLE_STAGE_LABELS } from './types';
import type { QosPolicy } from './types';

export const qosPolicyColumns: Column<QosPolicy>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/mpls/qos-policies/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'policyName',
    header: 'Policy Name',
    sortable: true,
    align: 'left',
    render: (row) => row.policyName ?? '—',
  },
  {
    key: 'classCount',
    header: 'Class Count',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.classCount),
  },
  {
    key: 'cirMbps',
    header: 'Cir Mbps',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.cirMbps),
  },
  {
    key: 'pirMbps',
    header: 'Pir Mbps',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.pirMbps),
  },
  {
    key: 'markingScheme',
    header: 'Marking Scheme',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.markingScheme ?? '—',
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
    render: (row) => row.lifecycleStage ? QOS_POLICY_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
  },
  {
    key: 'sourceSystem',
    header: 'Source System',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.sourceSystem ?? '—',
  },
  {
    key: 'region',
    header: 'Region',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.region ?? '—',
  },
  {
    key: 'owner',
    header: 'Owner',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.owner ?? '—',
  },
];

export const qosPolicyDefaultVisible = qosPolicyColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
