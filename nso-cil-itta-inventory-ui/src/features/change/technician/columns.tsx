import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { TECHNICIAN_CRITICALITY_LABELS } from './types';
import type { Technician } from './types';

export const technicianColumns: Column<Technician>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/change/technicians/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'employeeId',
    header: 'Employee Id',
    sortable: true,
    align: 'left',
    render: (row) => row.employeeId ?? '—',
  },
  {
    key: 'fullName',
    header: 'Full Name',
    sortable: true,
    align: 'left',
    render: (row) => row.fullName ?? '—',
  },
  {
    key: 'skillSet',
    header: 'Skill Set',
    sortable: true,
    align: 'left',
    render: (row) => row.skillSet ?? '—',
  },
  {
    key: 'homeSiteId',
    header: 'Home Site Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.homeSiteId} />,
  },
  {
    key: 'certificationLevel',
    header: 'Certification Level',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatNumber(row.certificationLevel),
  },
  {
    key: 'criticality',
    header: 'Criticality',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.criticality ? TECHNICIAN_CRITICALITY_LABELS[row.criticality] : '—',
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

export const technicianDefaultVisible = technicianColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
