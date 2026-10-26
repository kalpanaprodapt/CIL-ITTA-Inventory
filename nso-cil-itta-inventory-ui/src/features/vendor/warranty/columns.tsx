import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { WARRANTY_WARRANTY_TYPE_LABELS } from './types';
import type { Warranty } from './types';

export const warrantyColumns: Column<Warranty>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/vendor/warranties/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'assetId',
    header: 'Asset Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.assetId} />,
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
    key: 'warrantyType',
    header: 'Warranty Type',
    sortable: true,
    align: 'left',
    render: (row) => row.warrantyType ? WARRANTY_WARRANTY_TYPE_LABELS[row.warrantyType] : '—',
  },
  {
    key: 'terms',
    header: 'Terms',
    sortable: false,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => truncate(row.terms, 60),
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
];

export const warrantyDefaultVisible = warrantyColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
