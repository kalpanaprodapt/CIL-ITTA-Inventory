import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { CABLE_CABLE_TYPE_LABELS, CABLE_CRITICALITY_LABELS } from './types';
import type { Cable } from './types';

export const cableColumns: Column<Cable>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/cabling/cables/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'cableType',
    header: 'Cable Type',
    sortable: true,
    align: 'left',
    render: (row) => row.cableType ? CABLE_CABLE_TYPE_LABELS[row.cableType] : '—',
  },
  {
    key: 'lengthM',
    header: 'Length M',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.lengthM),
  },
  {
    key: 'aEndPortId',
    header: 'A End Port Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.aEndPortId} />,
  },
  {
    key: 'zEndPortId',
    header: 'Z End Port Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.zEndPortId} />,
  },
  {
    key: 'color',
    header: 'Color',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.color ?? '—',
  },
  {
    key: 'label',
    header: 'Label',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.label ?? '—',
  },
  {
    key: 'criticality',
    header: 'Criticality',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.criticality ? CABLE_CRITICALITY_LABELS[row.criticality] : '—',
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
  {
    key: 'effectiveTo',
    header: 'Effective To',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => formatDate(row.effectiveTo),
  },
];

export const cableDefaultVisible = cableColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
