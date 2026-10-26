import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { SKU_CRITICALITY_LABELS } from './types';
import type { Sku } from './types';

export const skuColumns: Column<Sku>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/vendor/skus/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'modelId',
    header: 'Model Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.modelId} />,
  },
  {
    key: 'skuCode',
    header: 'Sku Code',
    sortable: true,
    align: 'left',
    render: (row) => row.skuCode ?? '—',
  },
  {
    key: 'listPrice',
    header: 'List Price',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.listPrice),
  },
  {
    key: 'currency',
    header: 'Currency',
    sortable: true,
    align: 'left',
    render: (row) => row.currency ?? '—',
  },
  {
    key: 'leadTimeDays',
    header: 'Lead Time Days',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatNumber(row.leadTimeDays),
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
    render: (row) => row.criticality ? SKU_CRITICALITY_LABELS[row.criticality] : '—',
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

export const skuDefaultVisible = skuColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
