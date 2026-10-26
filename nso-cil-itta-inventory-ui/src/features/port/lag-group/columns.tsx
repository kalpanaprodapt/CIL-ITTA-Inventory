import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { LAG_GROUP_LACP_MODE_LABELS, LAG_GROUP_LIFECYCLE_STAGE_LABELS } from './types';
import type { LagGroup } from './types';

export const lagGroupColumns: Column<LagGroup>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/port/lag-groups/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'deviceId',
    header: 'Device Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.deviceId} />,
  },
  {
    key: 'lagName',
    header: 'Lag Name',
    sortable: true,
    align: 'left',
    render: (row) => row.lagName ?? '—',
  },
  {
    key: 'memberCount',
    header: 'Member Count',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.memberCount),
  },
  {
    key: 'lacpMode',
    header: 'Lacp Mode',
    sortable: true,
    align: 'left',
    render: (row) => row.lacpMode ? LAG_GROUP_LACP_MODE_LABELS[row.lacpMode] : '—',
  },
  {
    key: 'minLinks',
    header: 'Min Links',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatNumber(row.minLinks),
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
    render: (row) => row.lifecycleStage ? LAG_GROUP_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
  },
  {
    key: 'sourceSystem',
    header: 'Source System',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.sourceSystem ?? '—',
  },
];

export const lagGroupDefaultVisible = lagGroupColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
