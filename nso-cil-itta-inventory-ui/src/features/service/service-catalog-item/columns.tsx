import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { SERVICE_CATALOG_ITEM_FAMILY_LABELS, SERVICE_CATALOG_ITEM_LIFECYCLE_STAGE_LABELS } from './types';
import type { ServiceCatalogItem } from './types';

export const serviceCatalogItemColumns: Column<ServiceCatalogItem>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/service/service-catalog-items/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'itemName',
    header: 'Item Name',
    sortable: true,
    align: 'left',
    render: (row) => row.itemName ?? '—',
  },
  {
    key: 'family',
    header: 'Family',
    sortable: true,
    align: 'left',
    render: (row) => row.family ? SERVICE_CATALOG_ITEM_FAMILY_LABELS[row.family] : '—',
  },
  {
    key: 'basePrice',
    header: 'Base Price',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.basePrice),
  },
  {
    key: 'billingUnit',
    header: 'Billing Unit',
    sortable: true,
    align: 'left',
    render: (row) => row.billingUnit ?? '—',
  },
  {
    key: 'orderable',
    header: 'Orderable',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => <BooleanCell value={row.orderable} />,
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
    render: (row) => row.lifecycleStage ? SERVICE_CATALOG_ITEM_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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
];

export const serviceCatalogItemDefaultVisible = serviceCatalogItemColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
