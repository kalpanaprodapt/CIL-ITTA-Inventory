import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { LINK_LINK_TYPE_LABELS, LINK_CRITICALITY_LABELS } from './types';
import type { Link } from './types';

export const linkColumns: Column<Link>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/topology/links/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'aInterfaceId',
    header: 'A Interface Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.aInterfaceId} />,
  },
  {
    key: 'zInterfaceId',
    header: 'Z Interface Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.zInterfaceId} />,
  },
  {
    key: 'linkType',
    header: 'Link Type',
    sortable: true,
    align: 'left',
    render: (row) => row.linkType ? LINK_LINK_TYPE_LABELS[row.linkType] : '—',
  },
  {
    key: 'speedMbps',
    header: 'Speed Mbps',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.speedMbps),
  },
  {
    key: 'discoveredBy',
    header: 'Discovered By',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.discoveredBy ?? '—',
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
    render: (row) => row.criticality ? LINK_CRITICALITY_LABELS[row.criticality] : '—',
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
];

export const linkDefaultVisible = linkColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
