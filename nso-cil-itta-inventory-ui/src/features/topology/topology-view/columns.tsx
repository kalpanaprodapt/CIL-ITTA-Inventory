import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { TOPOLOGY_VIEW_VIEW_TYPE_LABELS, TOPOLOGY_VIEW_LIFECYCLE_STAGE_LABELS } from './types';
import type { TopologyView } from './types';

export const topologyViewColumns: Column<TopologyView>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/topology/topology-views/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'viewName',
    header: 'View Name',
    sortable: true,
    align: 'left',
    render: (row) => row.viewName ?? '—',
  },
  {
    key: 'viewType',
    header: 'View Type',
    sortable: true,
    align: 'left',
    render: (row) => row.viewType ? TOPOLOGY_VIEW_VIEW_TYPE_LABELS[row.viewType] : '—',
  },
  {
    key: 'scopeFilter',
    header: 'Scope Filter',
    sortable: true,
    align: 'left',
    render: (row) => row.scopeFilter ?? '—',
  },
  {
    key: 'refreshedAt',
    header: 'Refreshed At',
    sortable: true,
    align: 'left',
    render: (row) => formatDateTime(row.refreshedAt),
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
    render: (row) => row.lifecycleStage ? TOPOLOGY_VIEW_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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
];

export const topologyViewDefaultVisible = topologyViewColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
