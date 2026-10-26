import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { TOPOLOGY_EDGE_LIFECYCLE_STAGE_LABELS } from './types';
import type { TopologyEdge } from './types';

export const topologyEdgeColumns: Column<TopologyEdge>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/topology/topology-edges/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'viewId',
    header: 'View Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.viewId} />,
  },
  {
    key: 'sourceNodeId',
    header: 'Source Node Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.sourceNodeId} />,
  },
  {
    key: 'targetNodeId',
    header: 'Target Node Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.targetNodeId} />,
  },
  {
    key: 'weight',
    header: 'Weight',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.weight),
  },
  {
    key: 'directed',
    header: 'Directed',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => <BooleanCell value={row.directed} />,
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
    render: (row) => row.lifecycleStage ? TOPOLOGY_EDGE_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
  },
];

export const topologyEdgeDefaultVisible = topologyEdgeColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
