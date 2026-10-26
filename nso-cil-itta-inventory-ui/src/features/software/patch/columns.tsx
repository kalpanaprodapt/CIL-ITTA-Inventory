import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { PATCH_SEVERITY_LABELS, PATCH_CRITICALITY_LABELS } from './types';
import type { Patch } from './types';

export const patchColumns: Column<Patch>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/software/patchs/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'versionId',
    header: 'Version Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.versionId} />,
  },
  {
    key: 'patchId',
    header: 'Patch Id',
    sortable: true,
    align: 'left',
    render: (row) => row.patchId ?? '—',
  },
  {
    key: 'severity',
    header: 'Severity',
    sortable: true,
    align: 'left',
    render: (row) => row.severity ? PATCH_SEVERITY_LABELS[row.severity] : '—',
  },
  {
    key: 'releasedOn',
    header: 'Released On',
    sortable: true,
    align: 'left',
    render: (row) => formatDate(row.releasedOn),
  },
  {
    key: 'rebootRequired',
    header: 'Reboot Required',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => <BooleanCell value={row.rebootRequired} />,
  },
  {
    key: 'criticality',
    header: 'Criticality',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.criticality ? PATCH_CRITICALITY_LABELS[row.criticality] : '—',
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

export const patchDefaultVisible = patchColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
