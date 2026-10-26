import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { NETWORK_DEVICE_DEVICE_ROLE_LABELS, NETWORK_DEVICE_LIFECYCLE_STAGE_LABELS } from './types';
import type { NetworkDevice } from './types';

export const networkDeviceColumns: Column<NetworkDevice>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/device/network-devices/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'hostname',
    header: 'Hostname',
    sortable: true,
    align: 'left',
    render: (row) => row.hostname ?? '—',
  },
  {
    key: 'deviceRole',
    header: 'Device Role',
    sortable: true,
    align: 'left',
    render: (row) => row.deviceRole ? NETWORK_DEVICE_DEVICE_ROLE_LABELS[row.deviceRole] : '—',
  },
  {
    key: 'mgmtIp',
    header: 'Mgmt Ip',
    sortable: true,
    align: 'left',
    render: (row) => row.mgmtIp ?? '—',
  },
  {
    key: 'serialNumber',
    header: 'Serial Number',
    sortable: true,
    align: 'left',
    render: (row) => row.serialNumber ?? '—',
  },
  {
    key: 'modelId',
    header: 'Model Id',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => <ShortId value={row.modelId} />,
  },
  {
    key: 'rackUnitId',
    header: 'Rack Unit Id',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => <ShortId value={row.rackUnitId} />,
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
    render: (row) => row.lifecycleStage ? NETWORK_DEVICE_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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

export const networkDeviceDefaultVisible = networkDeviceColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
