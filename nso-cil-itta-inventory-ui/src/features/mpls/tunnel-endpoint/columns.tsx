import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { TUNNEL_ENDPOINT_TUNNEL_TYPE_LABELS, TUNNEL_ENDPOINT_LIFECYCLE_STAGE_LABELS } from './types';
import type { TunnelEndpoint } from './types';

export const tunnelEndpointColumns: Column<TunnelEndpoint>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/mpls/tunnel-endpoints/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'tunnelName',
    header: 'Tunnel Name',
    sortable: true,
    align: 'left',
    render: (row) => row.tunnelName ?? '—',
  },
  {
    key: 'tunnelType',
    header: 'Tunnel Type',
    sortable: true,
    align: 'left',
    render: (row) => row.tunnelType ? TUNNEL_ENDPOINT_TUNNEL_TYPE_LABELS[row.tunnelType] : '—',
  },
  {
    key: 'sourceIp',
    header: 'Source Ip',
    sortable: true,
    align: 'left',
    render: (row) => row.sourceIp ?? '—',
  },
  {
    key: 'destinationIp',
    header: 'Destination Ip',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.destinationIp ?? '—',
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
    render: (row) => row.lifecycleStage ? TUNNEL_ENDPOINT_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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

export const tunnelEndpointDefaultVisible = tunnelEndpointColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
