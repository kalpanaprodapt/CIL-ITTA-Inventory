import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { RACK_UNIT_FACE_LABELS, RACK_UNIT_CRITICALITY_LABELS } from './types';
import type { RackUnit } from './types';

export const rackUnitColumns: Column<RackUnit>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/facility/rack-units/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'rackId',
    header: 'Rack Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.rackId} />,
  },
  {
    key: 'unitNumber',
    header: 'Unit Number',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.unitNumber),
  },
  {
    key: 'occupied',
    header: 'Occupied',
    sortable: true,
    align: 'left',
    render: (row) => <BooleanCell value={row.occupied} />,
  },
  {
    key: 'mountedDeviceId',
    header: 'Mounted Device Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.mountedDeviceId} />,
  },
  {
    key: 'face',
    header: 'Face',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.face ? RACK_UNIT_FACE_LABELS[row.face] : '—',
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
    render: (row) => row.criticality ? RACK_UNIT_CRITICALITY_LABELS[row.criticality] : '—',
  },
  {
    key: 'costCenter',
    header: 'Cost Center',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.costCenter ?? '—',
  },
];

export const rackUnitDefaultVisible = rackUnitColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
