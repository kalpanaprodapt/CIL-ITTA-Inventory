import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { TOPOLOGY_NODE_CRITICALITY_LABELS } from './types';
import type { TopologyNode } from './types';

export const topologyNodeColumns: Column<TopologyNode>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/topology/topology-nodes/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'entityRef',
    header: 'Entity Ref',
    sortable: true,
    align: 'left',
    render: (row) => row.entityRef ?? '—',
  },
  {
    key: 'nodeType',
    header: 'Node Type',
    sortable: true,
    align: 'left',
    render: (row) => row.nodeType ?? '—',
  },
  {
    key: 'xPos',
    header: 'X Pos',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.xPos),
  },
  {
    key: 'yPos',
    header: 'Y Pos',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatDecimal(row.yPos),
  },
  {
    key: 'layer',
    header: 'Layer',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatNumber(row.layer),
  },
  {
    key: 'criticality',
    header: 'Criticality',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.criticality ? TOPOLOGY_NODE_CRITICALITY_LABELS[row.criticality] : '—',
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
];

export const topologyNodeDefaultVisible = topologyNodeColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
