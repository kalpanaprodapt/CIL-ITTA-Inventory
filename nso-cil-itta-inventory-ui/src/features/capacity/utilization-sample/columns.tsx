import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { UTILIZATION_SAMPLE_LIFECYCLE_STAGE_LABELS, UTILIZATION_SAMPLE_CRITICALITY_LABELS } from './types';
import type { UtilizationSample } from './types';

export const utilizationSampleColumns: Column<UtilizationSample>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/capacity/utilization-samples/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'targetRef',
    header: 'Target Ref',
    sortable: true,
    align: 'left',
    render: (row) => row.targetRef ?? '—',
  },
  {
    key: 'metric',
    header: 'Metric',
    sortable: true,
    align: 'left',
    render: (row) => row.metric ?? '—',
  },
  {
    key: 'sampledAt',
    header: 'Sampled At',
    sortable: true,
    align: 'left',
    render: (row) => formatDateTime(row.sampledAt),
  },
  {
    key: 'valuePct',
    header: 'Value Pct',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.valuePct),
  },
  {
    key: 'peak',
    header: 'Peak',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => <BooleanCell value={row.peak} />,
  },
  {
    key: 'lifecycleStage',
    header: 'Lifecycle Stage',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.lifecycleStage ? UTILIZATION_SAMPLE_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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
    render: (row) => row.criticality ? UTILIZATION_SAMPLE_CRITICALITY_LABELS[row.criticality] : '—',
  },
];

export const utilizationSampleDefaultVisible = utilizationSampleColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
