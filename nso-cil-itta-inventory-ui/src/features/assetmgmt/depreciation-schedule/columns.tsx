import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { DEPRECIATION_SCHEDULE_METHOD_LABELS, DEPRECIATION_SCHEDULE_LIFECYCLE_STAGE_LABELS } from './types';
import type { DepreciationSchedule } from './types';

export const depreciationScheduleColumns: Column<DepreciationSchedule>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/assetmgmt/depreciation-schedules/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'method',
    header: 'Method',
    sortable: true,
    align: 'left',
    render: (row) => row.method ? DEPRECIATION_SCHEDULE_METHOD_LABELS[row.method] : '—',
  },
  {
    key: 'usefulLifeMonths',
    header: 'Useful Life Months',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.usefulLifeMonths),
  },
  {
    key: 'salvageValue',
    header: 'Salvage Value',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.salvageValue),
  },
  {
    key: 'startDate',
    header: 'Start Date',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => formatDate(row.startDate),
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
    render: (row) => row.lifecycleStage ? DEPRECIATION_SCHEDULE_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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

export const depreciationScheduleDefaultVisible = depreciationScheduleColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
