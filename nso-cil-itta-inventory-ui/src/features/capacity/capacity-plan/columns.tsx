import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { CAPACITY_PLAN_LIFECYCLE_STAGE_LABELS } from './types';
import type { CapacityPlan } from './types';

export const capacityPlanColumns: Column<CapacityPlan>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/capacity/capacity-plans/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'planName',
    header: 'Plan Name',
    sortable: true,
    align: 'left',
    render: (row) => row.planName ?? '—',
  },
  {
    key: 'horizonMonths',
    header: 'Horizon Months',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.horizonMonths),
  },
  {
    key: 'targetUtilizationPct',
    header: 'Target Utilization Pct',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.targetUtilizationPct),
  },
  {
    key: 'owner',
    header: 'Owner',
    sortable: true,
    align: 'left',
    render: (row) => row.owner ?? '—',
  },
  {
    key: 'approvedOn',
    header: 'Approved On',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => formatDate(row.approvedOn),
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
    render: (row) => row.lifecycleStage ? CAPACITY_PLAN_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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

export const capacityPlanDefaultVisible = capacityPlanColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
