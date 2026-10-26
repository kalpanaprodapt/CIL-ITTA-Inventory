import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { IP_SUBNET_IP_VERSION_LABELS, IP_SUBNET_CRITICALITY_LABELS } from './types';
import type { IpSubnet } from './types';

export const ipSubnetColumns: Column<IpSubnet>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/ipam/ip-subnets/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'cidr',
    header: 'Cidr',
    sortable: true,
    align: 'left',
    render: (row) => row.cidr ?? '—',
  },
  {
    key: 'ipVersion',
    header: 'Ip Version',
    sortable: true,
    align: 'left',
    render: (row) => row.ipVersion ? IP_SUBNET_IP_VERSION_LABELS[row.ipVersion] : '—',
  },
  {
    key: 'vrfId',
    header: 'Vrf Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.vrfId} />,
  },
  {
    key: 'gateway',
    header: 'Gateway',
    sortable: true,
    align: 'left',
    render: (row) => row.gateway ?? '—',
  },
  {
    key: 'vlanId',
    header: 'Vlan Id',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => <ShortId value={row.vlanId} />,
  },
  {
    key: 'utilizationPct',
    header: 'Utilization Pct',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatDecimal(row.utilizationPct),
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
    render: (row) => row.criticality ? IP_SUBNET_CRITICALITY_LABELS[row.criticality] : '—',
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

export const ipSubnetDefaultVisible = ipSubnetColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
