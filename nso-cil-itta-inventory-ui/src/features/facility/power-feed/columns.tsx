import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { POWER_FEED_FEED_TYPE_LABELS, POWER_FEED_CRITICALITY_LABELS } from './types';
import type { PowerFeed } from './types';

export const powerFeedColumns: Column<PowerFeed>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/facility/power-feeds/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'roomId',
    header: 'Room Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.roomId} />,
  },
  {
    key: 'feedType',
    header: 'Feed Type',
    sortable: true,
    align: 'left',
    render: (row) => row.feedType ? POWER_FEED_FEED_TYPE_LABELS[row.feedType] : '—',
  },
  {
    key: 'voltage',
    header: 'Voltage',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.voltage),
  },
  {
    key: 'amperage',
    header: 'Amperage',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.amperage),
  },
  {
    key: 'phase',
    header: 'Phase',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.phase ?? '—',
  },
  {
    key: 'breakerLabel',
    header: 'Breaker Label',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.breakerLabel ?? '—',
  },
  {
    key: 'redundancyGroup',
    header: 'Redundancy Group',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.redundancyGroup ?? '—',
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
    render: (row) => row.criticality ? POWER_FEED_CRITICALITY_LABELS[row.criticality] : '—',
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

export const powerFeedDefaultVisible = powerFeedColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
