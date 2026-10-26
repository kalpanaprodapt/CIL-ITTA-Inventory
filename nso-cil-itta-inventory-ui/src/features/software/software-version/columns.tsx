import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { SOFTWARE_VERSION_RELEASE_TRAIN_LABELS, SOFTWARE_VERSION_CRITICALITY_LABELS } from './types';
import type { SoftwareVersion } from './types';

export const softwareVersionColumns: Column<SoftwareVersion>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/software/software-versions/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'imageId',
    header: 'Image Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.imageId} />,
  },
  {
    key: 'versionString',
    header: 'Version String',
    sortable: true,
    align: 'left',
    render: (row) => row.versionString ?? '—',
  },
  {
    key: 'releaseDate',
    header: 'Release Date',
    sortable: true,
    align: 'left',
    render: (row) => formatDate(row.releaseDate),
  },
  {
    key: 'endOfSupport',
    header: 'End Of Support',
    sortable: true,
    align: 'left',
    render: (row) => formatDate(row.endOfSupport),
  },
  {
    key: 'releaseTrain',
    header: 'Release Train',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.releaseTrain ? SOFTWARE_VERSION_RELEASE_TRAIN_LABELS[row.releaseTrain] : '—',
  },
  {
    key: 'cveCount',
    header: 'Cve Count',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatNumber(row.cveCount),
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
    render: (row) => row.criticality ? SOFTWARE_VERSION_CRITICALITY_LABELS[row.criticality] : '—',
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

export const softwareVersionDefaultVisible = softwareVersionColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
