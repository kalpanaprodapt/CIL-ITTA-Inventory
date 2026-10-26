import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { DNS_RECORD_RECORD_TYPE_LABELS, DNS_RECORD_CRITICALITY_LABELS } from './types';
import type { DnsRecord } from './types';

export const dnsRecordColumns: Column<DnsRecord>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/ipam/dns-records/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'zoneName',
    header: 'Zone Name',
    sortable: true,
    align: 'left',
    render: (row) => row.zoneName ?? '—',
  },
  {
    key: 'recordType',
    header: 'Record Type',
    sortable: true,
    align: 'left',
    render: (row) => row.recordType ? DNS_RECORD_RECORD_TYPE_LABELS[row.recordType] : '—',
  },
  {
    key: 'recordValue',
    header: 'Record Value',
    sortable: true,
    align: 'left',
    render: (row) => row.recordValue ?? '—',
  },
  {
    key: 'ttl',
    header: 'Ttl',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.ttl),
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
    render: (row) => row.criticality ? DNS_RECORD_CRITICALITY_LABELS[row.criticality] : '—',
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

export const dnsRecordDefaultVisible = dnsRecordColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
