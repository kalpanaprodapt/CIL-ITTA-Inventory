import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { ROUTE_TARGET_RT_TYPE_LABELS, ROUTE_TARGET_LIFECYCLE_STAGE_LABELS, ROUTE_TARGET_CRITICALITY_LABELS } from './types';
import type { RouteTarget } from './types';

export const routeTargetColumns: Column<RouteTarget>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/switching/route-targets/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'asn',
    header: 'Asn',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.asn),
  },
  {
    key: 'assignedNumber',
    header: 'Assigned Number',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.assignedNumber),
  },
  {
    key: 'rtType',
    header: 'Rt Type',
    sortable: true,
    align: 'left',
    render: (row) => row.rtType ? ROUTE_TARGET_RT_TYPE_LABELS[row.rtType] : '—',
  },
  {
    key: 'vrfId',
    header: 'Vrf Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.vrfId} />,
  },
  {
    key: 'lifecycleStage',
    header: 'Lifecycle Stage',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.lifecycleStage ? ROUTE_TARGET_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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
    render: (row) => row.criticality ? ROUTE_TARGET_CRITICALITY_LABELS[row.criticality] : '—',
  },
];

export const routeTargetDefaultVisible = routeTargetColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
