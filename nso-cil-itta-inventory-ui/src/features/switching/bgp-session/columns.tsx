import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { BGP_SESSION_SESSION_STATE_LABELS, BGP_SESSION_CRITICALITY_LABELS } from './types';
import type { BgpSession } from './types';

export const bgpSessionColumns: Column<BgpSession>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/switching/bgp-sessions/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'localDeviceId',
    header: 'Local Device Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.localDeviceId} />,
  },
  {
    key: 'peerAddress',
    header: 'Peer Address',
    sortable: true,
    align: 'left',
    render: (row) => row.peerAddress ?? '—',
  },
  {
    key: 'localAsn',
    header: 'Local Asn',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.localAsn),
  },
  {
    key: 'remoteAsn',
    header: 'Remote Asn',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.remoteAsn),
  },
  {
    key: 'sessionState',
    header: 'Session State',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.sessionState ? BGP_SESSION_SESSION_STATE_LABELS[row.sessionState] : '—',
  },
  {
    key: 'prefixLimit',
    header: 'Prefix Limit',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatNumber(row.prefixLimit),
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
    render: (row) => row.criticality ? BGP_SESSION_CRITICALITY_LABELS[row.criticality] : '—',
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

export const bgpSessionDefaultVisible = bgpSessionColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
