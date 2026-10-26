import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { SERVICE_INSTANCE_SERVICE_STATE_LABELS, SERVICE_INSTANCE_LIFECYCLE_STAGE_LABELS, SERVICE_INSTANCE_CRITICALITY_LABELS } from './types';
import type { ServiceInstance } from './types';

export const serviceInstanceColumns: Column<ServiceInstance>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/service/service-instances/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'catalogItemId',
    header: 'Catalog Item Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.catalogItemId} />,
  },
  {
    key: 'customerId',
    header: 'Customer Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.customerId} />,
  },
  {
    key: 'activatedOn',
    header: 'Activated On',
    sortable: true,
    align: 'left',
    render: (row) => formatDate(row.activatedOn),
  },
  {
    key: 'bandwidthMbps',
    header: 'Bandwidth Mbps',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.bandwidthMbps),
  },
  {
    key: 'serviceState',
    header: 'Service State',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.serviceState ? SERVICE_INSTANCE_SERVICE_STATE_LABELS[row.serviceState] : '—',
  },
  {
    key: 'lifecycleStage',
    header: 'Lifecycle Stage',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.lifecycleStage ? SERVICE_INSTANCE_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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
    render: (row) => row.criticality ? SERVICE_INSTANCE_CRITICALITY_LABELS[row.criticality] : '—',
  },
];

export const serviceInstanceDefaultVisible = serviceInstanceColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
