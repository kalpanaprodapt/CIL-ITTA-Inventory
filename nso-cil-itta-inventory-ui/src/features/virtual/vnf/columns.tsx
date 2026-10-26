import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { VNF_LIFECYCLE_STAGE_LABELS, VNF_CRITICALITY_LABELS } from './types';
import type { Vnf } from './types';

export const vnfColumns: Column<Vnf>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/virtual/vnfs/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'vnfName',
    header: 'Vnf Name',
    sortable: true,
    align: 'left',
    render: (row) => row.vnfName ?? '—',
  },
  {
    key: 'vnfType',
    header: 'Vnf Type',
    sortable: true,
    align: 'left',
    render: (row) => row.vnfType ?? '—',
  },
  {
    key: 'flavor',
    header: 'Flavor',
    sortable: true,
    align: 'left',
    render: (row) => row.flavor ?? '—',
  },
  {
    key: 'vcpu',
    header: 'Vcpu',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.vcpu),
  },
  {
    key: 'memoryGb',
    header: 'Memory Gb',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatNumber(row.memoryGb),
  },
  {
    key: 'instanceCount',
    header: 'Instance Count',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatNumber(row.instanceCount),
  },
  {
    key: 'lifecycleStage',
    header: 'Lifecycle Stage',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.lifecycleStage ? VNF_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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
  {
    key: 'criticality',
    header: 'Criticality',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.criticality ? VNF_CRITICALITY_LABELS[row.criticality] : '—',
  },
];

export const vnfDefaultVisible = vnfColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
