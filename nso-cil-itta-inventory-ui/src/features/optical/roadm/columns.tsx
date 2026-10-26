import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { ROADM_CRITICALITY_LABELS } from './types';
import type { Roadm } from './types';

export const roadmColumns: Column<Roadm>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/optical/roadms/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'systemId',
    header: 'System Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.systemId} />,
  },
  {
    key: 'degrees',
    header: 'Degrees',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.degrees),
  },
  {
    key: 'colorless',
    header: 'Colorless',
    sortable: true,
    align: 'left',
    render: (row) => <BooleanCell value={row.colorless} />,
  },
  {
    key: 'directionless',
    header: 'Directionless',
    sortable: true,
    align: 'left',
    render: (row) => <BooleanCell value={row.directionless} />,
  },
  {
    key: 'contentionless',
    header: 'Contentionless',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => <BooleanCell value={row.contentionless} />,
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
    render: (row) => row.criticality ? ROADM_CRITICALITY_LABELS[row.criticality] : '—',
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
];

export const roadmDefaultVisible = roadmColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
