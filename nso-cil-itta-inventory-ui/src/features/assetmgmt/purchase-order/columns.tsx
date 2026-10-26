import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { PURCHASE_ORDER_CRITICALITY_LABELS } from './types';
import type { PurchaseOrder } from './types';

export const purchaseOrderColumns: Column<PurchaseOrder>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/assetmgmt/purchase-orders/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'poNumber',
    header: 'Po Number',
    sortable: true,
    align: 'left',
    render: (row) => row.poNumber ?? '—',
  },
  {
    key: 'vendorId',
    header: 'Vendor Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.vendorId} />,
  },
  {
    key: 'orderedOn',
    header: 'Ordered On',
    sortable: true,
    align: 'left',
    render: (row) => formatDate(row.orderedOn),
  },
  {
    key: 'totalAmount',
    header: 'Total Amount',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.totalAmount),
  },
  {
    key: 'currency',
    header: 'Currency',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.currency ?? '—',
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
    render: (row) => row.criticality ? PURCHASE_ORDER_CRITICALITY_LABELS[row.criticality] : '—',
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

export const purchaseOrderDefaultVisible = purchaseOrderColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
