import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { K8S_CLUSTER_LIFECYCLE_STAGE_LABELS } from './types';
import type { K8sCluster } from './types';

export const k8sClusterColumns: Column<K8sCluster>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/virtual/k8s-clusters/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'clusterName',
    header: 'Cluster Name',
    sortable: true,
    align: 'left',
    render: (row) => row.clusterName ?? '—',
  },
  {
    key: 'clusterVersion',
    header: 'Cluster Version',
    sortable: true,
    align: 'left',
    render: (row) => row.clusterVersion ?? '—',
  },
  {
    key: 'nodeCount',
    header: 'Node Count',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.nodeCount),
  },
  {
    key: 'cloudAccountId',
    header: 'Cloud Account Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.cloudAccountId} />,
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
    key: 'apiEndpoint',
    header: 'Api Endpoint',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.apiEndpoint ?? '—',
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
    render: (row) => row.lifecycleStage ? K8S_CLUSTER_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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
    key: 'owner',
    header: 'Owner',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.owner ?? '—',
  },
];

export const k8sClusterDefaultVisible = k8sClusterColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
