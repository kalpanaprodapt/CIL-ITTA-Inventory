import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { CONTRACT_CRITICALITY_LABELS } from './types';
import type { Contract } from './types';

export const contractColumns: Column<Contract>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/customer/contracts/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'customerId',
    header: 'Customer Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.customerId} />,
  },
  {
    key: 'contractNumber',
    header: 'Contract Number',
    sortable: true,
    align: 'left',
    render: (row) => row.contractNumber ?? '—',
  },
  {
    key: 'startDate',
    header: 'Start Date',
    sortable: true,
    align: 'left',
    render: (row) => formatDate(row.startDate),
  },
  {
    key: 'endDate',
    header: 'End Date',
    sortable: true,
    align: 'left',
    render: (row) => formatDate(row.endDate),
  },
  {
    key: 'termMonths',
    header: 'Term Months',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatNumber(row.termMonths),
  },
  {
    key: 'autoRenew',
    header: 'Auto Renew',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => <BooleanCell value={row.autoRenew} />,
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
    render: (row) => row.criticality ? CONTRACT_CRITICALITY_LABELS[row.criticality] : '—',
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

export const contractDefaultVisible = contractColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
