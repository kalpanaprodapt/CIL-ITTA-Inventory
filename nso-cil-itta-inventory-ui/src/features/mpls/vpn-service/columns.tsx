import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { VPN_SERVICE_VPN_TYPE_LABELS, VPN_SERVICE_TOPOLOGY_LABELS, VPN_SERVICE_CRITICALITY_LABELS } from './types';
import type { VpnService } from './types';

export const vpnServiceColumns: Column<VpnService>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/mpls/vpn-services/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'vpnName',
    header: 'Vpn Name',
    sortable: true,
    align: 'left',
    render: (row) => row.vpnName ?? '—',
  },
  {
    key: 'vpnType',
    header: 'Vpn Type',
    sortable: true,
    align: 'left',
    render: (row) => row.vpnType ? VPN_SERVICE_VPN_TYPE_LABELS[row.vpnType] : '—',
  },
  {
    key: 'vrfId',
    header: 'Vrf Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.vrfId} />,
  },
  {
    key: 'customerId',
    header: 'Customer Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.customerId} />,
  },
  {
    key: 'topology',
    header: 'Topology',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.topology ? VPN_SERVICE_TOPOLOGY_LABELS[row.topology] : '—',
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
    render: (row) => row.criticality ? VPN_SERVICE_CRITICALITY_LABELS[row.criticality] : '—',
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
];

export const vpnServiceDefaultVisible = vpnServiceColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
