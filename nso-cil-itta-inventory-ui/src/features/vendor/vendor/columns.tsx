import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { VENDOR_TIER_LABELS, VENDOR_LIFECYCLE_STAGE_LABELS } from './types';
import type { Vendor } from './types';

export const vendorColumns: Column<Vendor>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/vendor/vendors/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'legalName',
    header: 'Legal Name',
    sortable: true,
    align: 'left',
    render: (row) => row.legalName ?? '—',
  },
  {
    key: 'supportEmail',
    header: 'Support Email',
    sortable: true,
    align: 'left',
    render: (row) => row.supportEmail ?? '—',
  },
  {
    key: 'supportPhone',
    header: 'Support Phone',
    sortable: true,
    align: 'left',
    render: (row) => row.supportPhone ?? '—',
  },
  {
    key: 'website',
    header: 'Website',
    sortable: true,
    align: 'left',
    render: (row) => row.website ?? '—',
  },
  {
    key: 'tier',
    header: 'Tier',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.tier ? VENDOR_TIER_LABELS[row.tier] : '—',
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
  {
    key: 'lifecycleStage',
    header: 'Lifecycle Stage',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.lifecycleStage ? VENDOR_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
  },
  {
    key: 'sourceSystem',
    header: 'Source System',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.sourceSystem ?? '—',
  },
];

export const vendorDefaultVisible = vendorColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
