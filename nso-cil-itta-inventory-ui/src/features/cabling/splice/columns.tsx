import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { SPLICE_LIFECYCLE_STAGE_LABELS, SPLICE_CRITICALITY_LABELS } from './types';
import type { Splice } from './types';

export const spliceColumns: Column<Splice>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/cabling/splices/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'enclosureId',
    header: 'Enclosure Id',
    sortable: true,
    align: 'left',
    render: (row) => row.enclosureId ?? '—',
  },
  {
    key: 'aStrandId',
    header: 'A Strand Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.aStrandId} />,
  },
  {
    key: 'zStrandId',
    header: 'Z Strand Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.zStrandId} />,
  },
  {
    key: 'lossDb',
    header: 'Loss Db',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.lossDb),
  },
  {
    key: 'splicedOn',
    header: 'Spliced On',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => formatDate(row.splicedOn),
  },
  {
    key: 'lifecycleStage',
    header: 'Lifecycle Stage',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.lifecycleStage ? SPLICE_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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
    render: (row) => row.criticality ? SPLICE_CRITICALITY_LABELS[row.criticality] : '—',
  },
];

export const spliceDefaultVisible = spliceColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
