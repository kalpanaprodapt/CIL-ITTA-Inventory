import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { IMPORT_BATCH_BATCH_STATE_LABELS, IMPORT_BATCH_LIFECYCLE_STAGE_LABELS } from './types';
import type { ImportBatch } from './types';

export const importBatchColumns: Column<ImportBatch>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/integration/import-batchs/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'fileName',
    header: 'File Name',
    sortable: true,
    align: 'left',
    render: (row) => row.fileName ?? '—',
  },
  {
    key: 'entityType',
    header: 'Entity Type',
    sortable: true,
    align: 'left',
    render: (row) => row.entityType ?? '—',
  },
  {
    key: 'totalRows',
    header: 'Total Rows',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.totalRows),
  },
  {
    key: 'processedRows',
    header: 'Processed Rows',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.processedRows),
  },
  {
    key: 'failedRows',
    header: 'Failed Rows',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatNumber(row.failedRows),
  },
  {
    key: 'batchState',
    header: 'Batch State',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.batchState ? IMPORT_BATCH_BATCH_STATE_LABELS[row.batchState] : '—',
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
    render: (row) => row.lifecycleStage ? IMPORT_BATCH_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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

export const importBatchDefaultVisible = importBatchColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
