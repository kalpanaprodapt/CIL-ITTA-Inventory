import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { PORT_MEDIA_TYPE_LABELS, PORT_ADMIN_STATE_LABELS, PORT_CRITICALITY_LABELS } from './types';
import type { Port } from './types';

export const portColumns: Column<Port>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/port/ports/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'cardId',
    header: 'Card Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.cardId} />,
  },
  {
    key: 'portNumber',
    header: 'Port Number',
    sortable: true,
    align: 'left',
    render: (row) => row.portNumber ?? '—',
  },
  {
    key: 'speedMbps',
    header: 'Speed Mbps',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.speedMbps),
  },
  {
    key: 'mediaType',
    header: 'Media Type',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.mediaType ? PORT_MEDIA_TYPE_LABELS[row.mediaType] : '—',
  },
  {
    key: 'adminState',
    header: 'Admin State',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.adminState ? PORT_ADMIN_STATE_LABELS[row.adminState] : '—',
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
    render: (row) => row.criticality ? PORT_CRITICALITY_LABELS[row.criticality] : '—',
  },
  {
    key: 'costCenter',
    header: 'Cost Center',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.costCenter ?? '—',
  },
];

export const portDefaultVisible = portColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
