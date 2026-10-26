import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { SUPPORT_CONTRACT_COVERAGE_LEVEL_LABELS, SUPPORT_CONTRACT_CRITICALITY_LABELS } from './types';
import type { SupportContract } from './types';

export const supportContractColumns: Column<SupportContract>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/vendor/support-contracts/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'vendorId',
    header: 'Vendor Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.vendorId} />,
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
    key: 'coverageLevel',
    header: 'Coverage Level',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.coverageLevel ? SUPPORT_CONTRACT_COVERAGE_LEVEL_LABELS[row.coverageLevel] : '—',
  },
  {
    key: 'annualCost',
    header: 'Annual Cost',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatDecimal(row.annualCost),
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
    render: (row) => row.criticality ? SUPPORT_CONTRACT_CRITICALITY_LABELS[row.criticality] : '—',
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

export const supportContractDefaultVisible = supportContractColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
