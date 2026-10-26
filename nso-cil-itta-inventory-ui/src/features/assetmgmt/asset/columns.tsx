import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { ASSET_LIFECYCLE_STAGE_LABELS } from './types';
import type { Asset } from './types';

export const assetColumns: Column<Asset>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/assetmgmt/assets/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'assetTag',
    header: 'Asset Tag',
    sortable: true,
    align: 'left',
    render: (row) => row.assetTag ?? '—',
  },
  {
    key: 'assetClass',
    header: 'Asset Class',
    sortable: true,
    align: 'left',
    render: (row) => row.assetClass ?? '—',
  },
  {
    key: 'acquiredOn',
    header: 'Acquired On',
    sortable: true,
    align: 'left',
    render: (row) => formatDate(row.acquiredOn),
  },
  {
    key: 'purchasePrice',
    header: 'Purchase Price',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.purchasePrice),
  },
  {
    key: 'currentValue',
    header: 'Current Value',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatDecimal(row.currentValue),
  },
  {
    key: 'custodian',
    header: 'Custodian',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.custodian ?? '—',
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
    render: (row) => row.lifecycleStage ? ASSET_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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
];

export const assetDefaultVisible = assetColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
