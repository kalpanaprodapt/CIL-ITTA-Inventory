import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { COUNTRY_CRITICALITY_LABELS } from './types';
import type { Country } from './types';

export const countryColumns: Column<Country>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/geography/countries/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'isoCode',
    header: 'Iso Code',
    sortable: true,
    align: 'left',
    render: (row) => row.isoCode ?? '—',
  },
  {
    key: 'isoNumeric',
    header: 'Iso Numeric',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.isoNumeric),
  },
  {
    key: 'dialCode',
    header: 'Dial Code',
    sortable: true,
    align: 'left',
    render: (row) => row.dialCode ?? '—',
  },
  {
    key: 'currency',
    header: 'Currency',
    sortable: true,
    align: 'left',
    render: (row) => row.currency ?? '—',
  },
  {
    key: 'regulatoryBody',
    header: 'Regulatory Body',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.regulatoryBody ?? '—',
  },
  {
    key: 'timezone',
    header: 'Timezone',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.timezone ?? '—',
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
    render: (row) => row.criticality ? COUNTRY_CRITICALITY_LABELS[row.criticality] : '—',
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

export const countryDefaultVisible = countryColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
