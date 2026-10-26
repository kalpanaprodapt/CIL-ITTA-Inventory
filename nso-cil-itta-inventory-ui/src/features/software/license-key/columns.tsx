import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { LICENSE_KEY_LIFECYCLE_STAGE_LABELS } from './types';
import type { LicenseKey } from './types';

export const licenseKeyColumns: Column<LicenseKey>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/software/license-keies/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'poolId',
    header: 'Pool Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.poolId} />,
  },
  {
    key: 'keyValue',
    header: 'Key Value',
    sortable: true,
    align: 'left',
    render: (row) => row.keyValue ?? '—',
  },
  {
    key: 'feature',
    header: 'Feature',
    sortable: true,
    align: 'left',
    render: (row) => row.feature ?? '—',
  },
  {
    key: 'expiresOn',
    header: 'Expires On',
    sortable: true,
    align: 'left',
    render: (row) => formatDate(row.expiresOn),
  },
  {
    key: 'assignedDeviceId',
    header: 'Assigned Device Id',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => <ShortId value={row.assignedDeviceId} />,
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
    render: (row) => row.lifecycleStage ? LICENSE_KEY_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
  },
];

export const licenseKeyDefaultVisible = licenseKeyColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
